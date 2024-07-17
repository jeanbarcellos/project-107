package com.jeanbarcellos.localidade.clients.ibge.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegiaoResponse {

    @EqualsAndHashCode.Include
    private Long id;

    private String sigla;

    private String nome;

}