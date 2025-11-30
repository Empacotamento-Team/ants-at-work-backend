package com.empacoters.antsback.logistics.domain.service;

import com.empacoters.antsback.logistics.domain.model.*;
import com.empacoters.antsback.logistics.domain.model.Package;
import com.empacoters.antsback.logistics.domain.repository.*;
import com.empacoters.antsback.logistics.interfaces.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OptimizationService {
    private static final Logger log = LoggerFactory.getLogger(OptimizationService.class);
    public static boolean processing = false;

    private final OptimizationRepository optimizationRepository;
    private final OptimizationQueueItemRepository optimizationQueueItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final TruckRepository truckRepository;
    private final PackageRepository packageRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${optimizer.url}")
    private String optimizerUrl;

    public OptimizationService(OptimizationQueueItemRepository optimizationQueueItemRepository, WebClient.Builder webClientBuilder, OptimizationRepository optimizationRepository, ShipmentRepository shipmentRepository, TruckRepository truckRepository, PackageRepository packageRepository) {
        this.optimizationQueueItemRepository = optimizationQueueItemRepository;
        this.webClientBuilder = webClientBuilder;
        this.optimizationRepository = optimizationRepository;
        this.shipmentRepository = shipmentRepository;
        this.truckRepository = truckRepository;
        this.packageRepository = packageRepository;
    }

    public void register(OptimizerRequestDTO dto) {
        ObjectMapper mapper = new ObjectMapper();
        String data;

        try {
            data = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        this.optimizationQueueItemRepository.save(new OptimizationQueueItem(
            null, OptimizationStatus.PENDING, 0,
            data, Instant.now(), Instant.now()
        ));
    }

    public void process(OptimizationQueueItem item) {
        if (!List.of(OptimizationStatus.PENDING, OptimizationStatus.FAILED).contains(item.status()))
            return;

        // Definindo status como em processamento
        item.changeStatus(OptimizationStatus.PROCESSING);
        optimizationQueueItemRepository.save(item);
        processing = true;

        var xlsxBuilder = getPartBuilder(item);
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

            this.saveOptimizerResponse(response, item.id());
        } catch (Exception e) {
            // Definindo status como falha
            log.error("e: ", e);
            item.changeStatus(OptimizationStatus.FAILED);
            item.changeAttempts(item.attempts() + 1);
            item.changeUpdatedAt(Instant.now());
            optimizationQueueItemRepository.save(item);
            processing = false;
            return;
        }

        // Definindo status como sucesso
        item.changeStatus(OptimizationStatus.SUCCESS);
        item.changeUpdatedAt(Instant.now());
        optimizationQueueItemRepository.save(item);
        processing = false;
    }

    private void saveOptimizerResponse(OptimizerResponseDTO response, Long queueItemId) {
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
            var truck = this.truckRepository.byId(Long.valueOf(container.id_container()));
            if (truck == null) {
                System.out.println("Container: " + container.id_container() + " not found");
                continue;
            }

            var load = new Load(
                null, null, truck, items,
                container.peso_total_alocado(), container.peso_restante_caixa(),
                container.volume_total_alocado(), container.gx(),
                container.gy(), container.gz()
            );
            for (var pkg : items) {
                pkg.changeLoadId(load.id());
            }

            shipment.addLoad(load);
        }

        this.shipmentRepository.save(shipment);
    }

    private List<Package> saveContainerItems(OptimizerContainerResponseDTO container) {
        List<Package> packages = new ArrayList<>();
        for (var item : container.itens()) {
            var originalPackage = this.packageRepository.findById(item.id_item().longValue());
            if (originalPackage == null) {
                System.out.println("Item " + item.id_item() + " not found");
                continue;
            }

            originalPackage.changeXPosition(item.x());
            originalPackage.changeYPosition(item.y());
            originalPackage.changeZPosition(item.z());
            originalPackage.changeOrientation(item.orientacao());

            packages.add(originalPackage);
        }

        return packages;
    }

    private MultipartBodyBuilder getPartBuilder(OptimizationQueueItem item) {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] xlsxBytes;
        try {
            var dto = objectMapper.readValue(item.requestData(), OptimizerRequestDTO.class);
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
            row.createCell(0).setCellValue(dtoRow.familyName());
            row.createCell(1).setCellValue(dtoRow.itemBatch());
            row.createCell(2).setCellValue(dtoRow.familyId());
            row.createCell(3).setCellValue(dtoRow.itemId());
            row.createCell(4).setCellValue(dtoRow.weight());
            row.createCell(5).setCellValue(dtoRow.height());
            row.createCell(6).setCellValue(dtoRow.width());
            row.createCell(7).setCellValue(dtoRow.length());
            row.createCell(8).setCellValue(dtoRow.supportedWeight());
            row.createCell(9).setCellValue(dtoRow.itemDescription());
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
        for (OptimizerRequestContainerRowDTO dtoRow : dto.containers()) {
            rowCount++;

            Row row = containersSheet.createRow(rowCount);
            row.createCell(0).setCellValue(dtoRow.containerId());
            row.createCell(1).setCellValue(dtoRow.length());
            row.createCell(2).setCellValue(dtoRow.width());
            row.createCell(3).setCellValue(dtoRow.height());
            row.createCell(4).setCellValue(dtoRow.supportedWeight());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }
}
