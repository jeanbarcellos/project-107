package com.jeanbarcellos.localidade.mapper;

import com.jeanbarcellos.localidade.dtos.ibge.MunicipioResponse;
import com.jeanbarcellos.localidade.dtos.ibge.UFResponse;
import com.jeanbarcellos.localidade.entities.Estado;
import com.jeanbarcellos.localidade.entities.Municipio;

public class LocalidadeMapper {

    private LocalidadeMapper() {
    }

    public static Estado toEstado(UFResponse response) {
        return Estado.builder()
                .id(response.getId())
                .nome(response.getNome())
                .sigla(response.getSigla())
                .build();
    }

    public static Municipio toMunicipio(MunicipioResponse response) {
        return Municipio.builder()
                .id(response.getId())
                .nome(response.getNome())
                .build();
    }

}
