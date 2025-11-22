package com.empacoters.antsback.logistics.interfaces.dto;

public record OptimizerContainerResponseDTO (
    Integer id_container,
    OptimizerItemResponseDTO itens,
    Integer num_itens,
    Double[] familias_presentes,
    Double peso_total_alocado,
    Double peso_restante_caixa,
    Double volume_total_alocado,
    Double ocupacao_volume_percentual,
    Double L_container,
    Double W_container,
    Double H_container,
    Double gx,
    Double gy,
    Double gz
) {}
