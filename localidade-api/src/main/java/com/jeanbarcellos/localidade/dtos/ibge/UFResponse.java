package com.jeanbarcellos.localidade.dtos.ibge;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UFResponse {

    @EqualsAndHashCode.Include
    private Long id;

    private String sigla;

    private String nome;

    private RegiaoResponse regiao;
}
