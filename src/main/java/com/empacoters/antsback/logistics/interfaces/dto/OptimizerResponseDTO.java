package com.empacoters.antsback.logistics.interfaces.dto;

public record OptimizerResponseDTO(
    String status_solver,
    String condicao_terminacao,
    Boolean solucao_encontrada,
    Integer num_conteineres_utilizados,
    Double penalidade_familias_misturadas,
    Double desvio_centro_gravidade,
    OptimizerContainerResponseDTO[] containers
) {}
