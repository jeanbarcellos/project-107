package com.jeanbarcellos.localidade.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {

    @EqualsAndHashCode.Include
    private Long id;

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private Municipio municipio;

}
