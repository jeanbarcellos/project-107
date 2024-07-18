package com.jeanbarcellos.project107.clients.ibge.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UFResponse {

    public static final String LABEL_ID = "id";
    public static final String LABEL_SIGLA = "sigla";
    public static final String LABEL_NOME = "nome";

    @EqualsAndHashCode.Include
    private Long id;

    private String sigla;

    private String nome;

    private RegiaoResponse regiao;
}
