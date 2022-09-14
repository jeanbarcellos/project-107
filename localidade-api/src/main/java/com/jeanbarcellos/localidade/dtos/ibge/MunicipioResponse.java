package com.jeanbarcellos.localidade.dtos.ibge;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MunicipioResponse {

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private MicrorregiaoResponse microrregiao;

    @JsonProperty("regiao-imediata")
    private RegiaoImediataResponse regiaoImediata;

}
