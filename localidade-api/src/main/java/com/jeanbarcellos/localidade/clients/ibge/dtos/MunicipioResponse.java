package com.jeanbarcellos.localidade.clients.ibge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MunicipioResponse {

    public static final String LABEL_ID = "id";
    public static final String LABEL_NOME = "nome";

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private MicrorregiaoResponse microrregiao;

    @JsonProperty("regiao-imediata")
    private RegiaoImediataResponse regiaoImediata;

    public UFResponse getUF() {
        return this.getMicrorregiao().getMesorregiao().getUf();
    }

}
