package com.jeanbarcellos.localidade.dtos.ibge;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MesorregiaoResponse {

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    @JsonProperty("UF")
    private UFResponse uf;
}
