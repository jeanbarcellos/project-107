package com.jeanbarcellos.localidade.clients.ibge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegiaoImediataResponse {

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    @JsonProperty("regiao-intermediaria")
    private RegiaoIntermediariaResponse regiaoIntermediaria;
}