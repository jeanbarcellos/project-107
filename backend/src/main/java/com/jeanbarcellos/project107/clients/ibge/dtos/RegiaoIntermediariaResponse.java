package com.jeanbarcellos.project107.clients.ibge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegiaoIntermediariaResponse {

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    @JsonProperty("UF")
    private UFResponse uf;

}
