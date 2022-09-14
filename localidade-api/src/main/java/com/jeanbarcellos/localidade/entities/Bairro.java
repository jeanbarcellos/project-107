package com.jeanbarcellos.localidade.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Bairro {

    @EqualsAndHashCode.Include
    private Long id;

    private String descricao;

    private Municipio municipio;

}
