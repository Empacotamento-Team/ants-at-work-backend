package com.empacoters.antsback.logistics.domain.service;

import com.empacoters.antsback.logistics.domain.model.*;
import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.domain.repository.*;
import com.empacoters.antsback.logistics.domain.repository.LoadRepository;
import com.empacoters.antsback.logistics.interfaces.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Service
public class OptimizationService {
    private static final Logger log = LoggerFactory.getLogger(OptimizationService.class);
    public static boolean processing = false;

    private final OptimizationRepository optimizationRepository;
    private final OptimizationQueueItemRepository optimizationQueueItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final TruckRepository truckRepository;
    private final PackageRepository packageRepository;
    private final LoadRepository loadRepository;
    private final WebClient.Builder webClientBuilder;
    private final ExecutorService executorService;

    @Value("${optimizer.url}")
    private String optimizerUrl;

    public OptimizationService(OptimizationQueueItemRepository optimizationQueueItemRepository, WebClient.Builder webClientBuilder, OptimizationRepository optimizationRepository, ShipmentRepository shipmentRepository, TruckRepository truckRepository, PackageRepository packageRepository, LoadRepository loadRepository, ExecutorService executorService) {
        this.optimizationQueueItemRepository = optimizationQueueItemRepository;
        this.webClientBuilder = webClientBuilder;
        this.optimizationRepository = optimizationRepository;
        this.shipmentRepository = shipmentRepository;
        this.truckRepository = truckRepository;
        this.packageRepository = packageRepository;
        this.loadRepository = loadRepository;
        this.executorService = executorService;
    }

    public OptimizationQueueItem register(OptimizerRequestDTO dto) {
        ObjectMapper mapper = new ObjectMapper();
        String data;

        try {
            data = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        var queueItem = this.optimizationQueueItemRepository.save(new OptimizationQueueItem(
            null, OptimizationStatus.PENDING, 0,
            data, Instant.now(), Instant.now()
        ));

        executorService.submit(() -> process(queueItem));
        return queueItem;
    }

    public OptimizationQueueItem register(OptimizerRequestDTO dto, String requestDataJson) {
        var queueItem = this.optimizationQueueItemRepository.save(new OptimizationQueueItem(
            null, OptimizationStatus.PENDING, 0,
            requestDataJson, Instant.now(), Instant.now()
        ));

        executorService.submit(() -> process(queueItem));
        return queueItem;
    }

    @Transactional
    public void process(OptimizationQueueItem item) {
        if (!List.of(OptimizationStatus.PENDING, OptimizationStatus.FAILED).contains(item.status()))
            return;

        // Definindo status como em processamento
        item.changeStatus(OptimizationStatus.PROCESSING);
        optimizationQueueItemRepository.save(item);
        processing = true;

        ObjectMapper objectMapper = new ObjectMapper();
        OptimizerRequestDTO dto;
        try {
            try {
                Map<String, Object> requestDataMap = objectMapper.readValue(item.requestData(), Map.class);
                Object itemsObj = requestDataMap.get("items");
                Object containersObj = requestDataMap.get("containers");
                
                if (itemsObj != null && containersObj != null) {
                    OptimizerRequestItemRowDTO[] items = objectMapper.convertValue(itemsObj, OptimizerRequestItemRowDTO[].class);
                    OptimizerRequestContainerRowDTO[] containers = objectMapper.convertValue(containersObj, OptimizerRequestContainerRowDTO[].class);
                    dto = new OptimizerRequestDTO(items, containers);
                } else {
                    dto = objectMapper.readValue(item.requestData(), OptimizerRequestDTO.class);
                }
            } catch (Exception e) {
                dto = objectMapper.readValue(item.requestData(), OptimizerRequestDTO.class);
            }
        } catch (JsonProcessingException e) {
            log.error("Error deserializing optimization request data for queue item {}: ", item.id(), e);
            item.changeStatus(OptimizationStatus.FAILED);
            item.changeAttempts(item.attempts() + 1);
            item.changeUpdatedAt(Instant.now());
            optimizationQueueItemRepository.save(item);
            processing = false;
            return;
        } catch (Exception e) {
            log.error("Error processing optimization request data for queue item {}: ", item.id(), e);
            item.changeStatus(OptimizationStatus.FAILED);
            item.changeAttempts(item.attempts() + 1);
            item.changeUpdatedAt(Instant.now());
            optimizationQueueItemRepository.save(item);
            processing = false;
            return;
        }
        
        // Create mapping from sequential ID to real truck ID
        Map<Integer, Long> containerIdMapping = new HashMap<>();
        int sequentialId = 1;
        for (var container : dto.containers()) {
            containerIdMapping.put(sequentialId, container.containerId());
            sequentialId++;
        }
        
        var xlsxBuilder = getPartBuilder(dto);
        
        // Log what we're sending
        log.info("Sending optimization request with {} items and {} containers", 
            dto.items().length, dto.containers().length);
        for (var requestItem : dto.items()) {
            log.debug("Sending item: id={}, weight={}, dimensions={}x{}x{}", 
                requestItem.itemId(), requestItem.weight(), requestItem.length(), requestItem.width(), requestItem.height());
        }
        for (var container : dto.containers()) {
            log.debug("Sending container: id={}, dimensions={}x{}x{}, capacity={}", 
                container.containerId(), container.length(), container.width(), 
                container.height(), container.supportedWeight());
        }
        
        try {
            var response = webClientBuilder.build().post()
                .uri(optimizerUrl + "/resolver_carregamento")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(xlsxBuilder.build())
                .retrieve()
                .bodyToMono(OptimizerResponseDTO.class)
                .block();
            
            if (response == null)
                throw new Exception("No response from optimizer");

            log.info("Received optimizer response: solucao_encontrada={}, num_conteineres={}, containers={}", 
                response.solucao_encontrada(), response.num_conteineres_utilizados(), response.containers().length);
            
            // Log all items from all containers to see what positions are being returned
            for (var container : response.containers()) {
                log.info("Container {} has {} items", container.id_container(), container.itens().length);
                for (var optimizerItem : container.itens()) {
                    log.info("Container {} - Item {}: x={}, y={}, z={}, orientacao={}", 
                        container.id_container(), optimizerItem.id_item(), optimizerItem.x(), optimizerItem.y(), optimizerItem.z(), optimizerItem.orientacao());
                }
            }

            this.saveOptimizerResponse(response, item.id(), containerIdMapping);
            
            // Definindo status como sucesso
            item.changeStatus(OptimizationStatus.SUCCESS);
            item.changeUpdatedAt(Instant.now());
            optimizationQueueItemRepository.save(item);
            log.info("Optimization {} completed successfully", item.id());
        } catch (Exception e) {
            // Definindo status como falha
            log.error("Error processing optimization {}: ", item.id(), e);
            item.changeStatus(OptimizationStatus.FAILED);
            item.changeAttempts(item.attempts() + 1);
            item.changeUpdatedAt(Instant.now());
            optimizationQueueItemRepository.save(item);
        } finally {
            processing = false;
        }
    }

    @PostConstruct
    private void recoverQueued() {
        var pendentes = optimizationQueueItemRepository.findAllByStatus(OptimizationStatus.PENDING);

        pendentes.forEach(this::process);
    }

    @Transactional
    private void saveOptimizerResponse(OptimizerResponseDTO response, Long queueItemId, Map<Integer, Long> containerIdMapping) {
        try {
            // Saving Optimization
            var optimization = new Optimization(
                null, queueItemId, response.status_solver(),
                response.condicao_terminacao(), response.solucao_encontrada(),
                response.num_conteineres_utilizados(), response.penalidade_familias_misturadas(),
                response.desvio_centro_gravidade(), Instant.now()
            );
            this.optimizationRepository.save(optimization);

            var shipment = this.shipmentRepository.save(new Shipment(null, new ArrayList<>(), Instant.now()));

            // Saving Loads
            for (var container : response.containers()) {
                var items = this.saveContainerItems(container);
                // Map sequential ID back to real truck ID
                Long realTruckId = containerIdMapping.get(container.id_container());
                if (realTruckId == null) {
                    log.warn("Container mapping not found for sequential ID: {}", container.id_container());
                    continue;
                }
                var truck = this.truckRepository.byId(realTruckId);
                if (truck == null) {
                    log.warn("Truck: {} not found", realTruckId);
                    continue;
                }

                // Calcular volume alocado baseado nas dimensões reais dos pacotes
                double calculatedVolume = calculateTotalAllocatedVolume(items);
                
                // Calcular porcentagem de ocupação baseada no volume do container
                var containerVolume = truck.internalDimensions().length() * 
                                     truck.internalDimensions().width() * 
                                     truck.internalDimensions().height();
                double calculatedOccupancy = containerVolume > 0 ? (calculatedVolume / containerVolume) * 100.0 : 0.0;

                var load = new Load(
                    null, shipment.id(), truck, items,
                    container.peso_total_alocado(), container.peso_restante_caixa(),
                    calculatedVolume, calculatedOccupancy,
                    container.gx(), container.gy(), container.gz()
                );
                
                // Save the load first to get its ID
                var savedLoad = this.loadRepository.save(load);
                
                // Associate packages with the load and save them with positions and loadId
                for (var pkg : items) {
                    // Store positions before changing loadId
                    Double xPos = pkg.xPosition();
                    Double yPos = pkg.yPosition();
                    Double zPos = pkg.zPosition();
                    String orientation = pkg.orientation();
                    
                    pkg.changeLoadId(savedLoad.id());
                    
                    // Ensure positions are still set
                    if (xPos != null) pkg.changeXPosition(xPos);
                    if (yPos != null) pkg.changeYPosition(yPos);
                    if (zPos != null) pkg.changeZPosition(zPos);
                    if (orientation != null) pkg.changeOrientation(orientation);
                    
                    log.debug("Saving package {} with loadId={}, x={}, y={}, z={}, orientation={}", 
                        pkg.id(), savedLoad.id(), pkg.xPosition(), pkg.yPosition(), pkg.zPosition(), pkg.orientation());
                    
                    // Save package with updated loadId and positions
                    var savedPkg = this.packageRepository.save(pkg);
                    
                    // Verify positions were saved correctly
                    if (xPos != null && xPos != 0.0 && (savedPkg.xPosition() == null || savedPkg.xPosition() == 0.0)) {
                        log.warn("Package {} x position was not saved correctly. Expected: {}, Got: {}", 
                            savedPkg.id(), xPos, savedPkg.xPosition());
                    }
                    if (yPos != null && yPos != 0.0 && (savedPkg.yPosition() == null || savedPkg.yPosition() == 0.0)) {
                        log.warn("Package {} y position was not saved correctly. Expected: {}, Got: {}", 
                            savedPkg.id(), yPos, savedPkg.yPosition());
                    }
                    if (zPos != null && zPos != 0.0 && (savedPkg.zPosition() == null || savedPkg.zPosition() == 0.0)) {
                        log.warn("Package {} z position was not saved correctly. Expected: {}, Got: {}", 
                            savedPkg.id(), zPos, savedPkg.zPosition());
                    }
                    log.debug("Package {} saved. Retrieved: x={}, y={}, z={}", 
                        savedPkg.id(), savedPkg.xPosition(), savedPkg.yPosition(), savedPkg.zPosition());
                }

                shipment.addLoad(savedLoad);
            }

            this.shipmentRepository.save(shipment);
            log.info("Successfully saved optimization response for queue item {}", queueItemId);
        } catch (Exception e) {
            log.error("Error saving optimizer response for queue item {}: ", queueItemId, e);
            throw e; // Re-throw to be caught by process method
        }
    }

    private List<Package> saveContainerItems(OptimizerContainerResponseDTO container) {
        List<Package> packages = new ArrayList<>();
        log.info("Processing container {} with {} items", container.id_container(), container.itens().length);
        
        for (var item : container.itens()) {
            log.info("Processing item {}: x={}, y={}, z={}, peso={}, orientacao={}", 
                item.id_item(), item.x(), item.y(), item.z(), item.peso(), item.orientacao());
            
            var originalPackage = this.packageRepository.findById(item.id_item().longValue());
            if (originalPackage == null) {
                log.warn("Item {} not found in database", item.id_item());
                continue;
            }

            // Update positions and orientation from optimizer response
            // O otimizador retorna posições em metros, mas precisamos salvar em cm
            // (já que as dimensões dos produtos são armazenadas em cm)
            double xInCm = item.x() * 100.0;
            double yInCm = item.y() * 100.0;
            double zInCm = item.z() * 100.0;
            
            log.info("Updating package {} (current: x={}, y={}, z={}) with new positions: x={}, y={}, z={}, orientation={}", 
                item.id_item(), 
                originalPackage.xPosition(), originalPackage.yPosition(), originalPackage.zPosition(),
                xInCm, yInCm, zInCm, item.orientacao());
            
            originalPackage.changeXPosition(xInCm);
            originalPackage.changeYPosition(yInCm);
            originalPackage.changeZPosition(zInCm);
            originalPackage.changeOrientation(item.orientacao());

            // Verify positions were set
            log.info("Package {} after update: x={}, y={}, z={}, orientation={}", 
                originalPackage.id(), 
                originalPackage.xPosition(), originalPackage.yPosition(), originalPackage.zPosition(), 
                originalPackage.orientation());

            // Don't save here - will save after associating with load
            packages.add(originalPackage);
        }

        return packages;
    }

    /**
     * Calcula o volume total alocado baseado nas dimensões reais dos pacotes.
     * Considera a orientação de cada pacote para calcular o volume correto.
     */
    private double calculateTotalAllocatedVolume(List<Package> packages) {
        double totalVolume = 0.0;
        
        for (var pkg : packages) {
            if (pkg.packaging() == null || pkg.packaging().internalDimensions() == null) {
                log.warn("Package {} has no packaging dimensions, skipping volume calculation", pkg.id());
                continue;
            }
            
            var dims = pkg.packaging().internalDimensions();
            // Dimensões em cm
            double length = dims.length() != null ? dims.length() : 0.0;
            double width = dims.width() != null ? dims.width() : 0.0;
            double height = dims.height() != null ? dims.height() : 0.0;
            
            // Aplicar orientação (a orientação afeta como as dimensões são usadas)
            // Mas o volume sempre é length * width * height, independente da orientação
            // A orientação só muda qual dimensão fica em qual eixo, mas o volume físico é o mesmo
            double packageVolume = length * width * height; // Volume em cm³
            
            // Converter de cm³ para m³
            double volumeInM3 = packageVolume / 1_000_000.0;
            
            totalVolume += volumeInM3;
        }
        
        log.debug("Calculated total allocated volume: {} m³ for {} packages", totalVolume, packages.size());
        return totalVolume;
    }

    private MultipartBodyBuilder getPartBuilder(OptimizerRequestDTO dto) {
        byte[] xlsxBytes;
        try {
            xlsxBytes = getXlsxBytes(dto);
            Files.write(Paths.get("debug.xlsx"), xlsxBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", xlsxBytes)
            .filename("dados.xlsx")
            .contentType(MediaType.APPLICATION_OCTET_STREAM);
        return builder;
    }

    private byte[] getXlsxBytes(OptimizerRequestDTO dto) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        // --- Items ---
        Sheet sheet = workbook.createSheet("Dados");

        // Headers
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("familia");
        headerRow.createCell(1).setCellValue("lote");
        headerRow.createCell(2).setCellValue("dep.");
        headerRow.createCell(3).setCellValue("ud");
        headerRow.createCell(4).setCellValue("peso_bruto_ud");
        headerRow.createCell(5).setCellValue("altura");
        headerRow.createCell(6).setCellValue("largura");
        headerRow.createCell(7).setCellValue("compr");
        headerRow.createCell(8).setCellValue("peso_sup.");
        headerRow.createCell(9).setCellValue("texto_breve_de_material");

        int rowCount = 0;
        for (OptimizerRequestItemRowDTO dtoRow : dto.items()) {
            rowCount++;

            Row row = sheet.createRow(rowCount);
            row.createCell(0).setCellValue(dtoRow.familyName() != null ? dtoRow.familyName() : "");
            row.createCell(1).setCellValue(dtoRow.itemBatch() != null ? dtoRow.itemBatch() : "");
            row.createCell(2).setCellValue(dtoRow.familyId());
            row.createCell(3).setCellValue(dtoRow.itemId());
            row.createCell(4).setCellValue(dtoRow.weight());
            row.createCell(5).setCellValue(dtoRow.height());
            row.createCell(6).setCellValue(dtoRow.width());
            row.createCell(7).setCellValue(dtoRow.length());
            row.createCell(8).setCellValue(dtoRow.supportedWeight());
            row.createCell(9).setCellValue(dtoRow.itemDescription() != null ? dtoRow.itemDescription() : "");
        }


        // --- Containers ---
        Sheet containersSheet = workbook.createSheet("Containers");

        // Headers
        Row headerRow2 = containersSheet.createRow(0);
        headerRow2.createCell(0).setCellValue("id");
        headerRow2.createCell(1).setCellValue("comprimento");
        headerRow2.createCell(2).setCellValue("largura");
        headerRow2.createCell(3).setCellValue("altura");
        headerRow2.createCell(4).setCellValue("capacidade_peso");

        rowCount = 0;
        int sequentialContainerId = 1;
        for (OptimizerRequestContainerRowDTO dtoRow : dto.containers()) {
            rowCount++;

            Row row = containersSheet.createRow(rowCount);
            // Use sequential ID (1, 2, 3...) instead of real truck ID
            row.createCell(0).setCellValue(sequentialContainerId);
            row.createCell(1).setCellValue(dtoRow.length());
            row.createCell(2).setCellValue(dtoRow.width());
            row.createCell(3).setCellValue(dtoRow.height());
            row.createCell(4).setCellValue(dtoRow.supportedWeight());
            sequentialContainerId++;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }
}
