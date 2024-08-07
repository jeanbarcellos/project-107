package com.jeanbarcellos.project107.clients.ibge.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MicrorregiaoResponse {

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private MesorregiaoResponse mesorregiao;

}
