package com.jeanbarcellos.localidade.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estado {

    @EqualsAndHashCode.Include
    private Long id;

    private String sigla;

    private String nome;

}
