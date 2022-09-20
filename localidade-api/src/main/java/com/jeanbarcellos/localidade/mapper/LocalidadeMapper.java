package com.jeanbarcellos.localidade.mapper;

import java.util.function.LongFunction;

import com.jeanbarcellos.localidade.dtos.EnderecoRequest;
import com.jeanbarcellos.localidade.dtos.ibge.MunicipioResponse;
import com.jeanbarcellos.localidade.dtos.ibge.UFResponse;
import com.jeanbarcellos.localidade.entities.Endereco;
import com.jeanbarcellos.localidade.entities.Estado;
import com.jeanbarcellos.localidade.entities.Municipio;

public class LocalidadeMapper {

    private LocalidadeMapper() {
    }

    public static Estado toEstado(UFResponse request) {
        return Estado.builder()
                .id(request.getId())
                .nome(request.getNome())
                .sigla(request.getSigla())
                .build();
    }

    public static Municipio toMunicipio(MunicipioResponse request) {
        return Municipio.builder()
                .id(request.getId())
                .nome(request.getNome())
                .build();
    }

    public static Endereco copyProperties(Endereco endereco, EnderecoRequest request,
            LongFunction<Municipio> municipioRepositoryFind) {
        return endereco
                .setId(request.getId())
                .setCep(request.getCep())
                .setMunicipio(municipioRepositoryFind.apply(request.getMunicipioId()))
                .setBairro(request.getBairro())
                .setLogradouro(request.getLogradouro())
                .setNumero(request.getNumero())
                .setComplemento(request.getComplemento());
    }

}
