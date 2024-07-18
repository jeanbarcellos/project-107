package com.jeanbarcellos.project107.mapper;

import java.util.function.LongFunction;

import com.jeanbarcellos.project107.clients.ibge.dtos.MunicipioResponse;
import com.jeanbarcellos.project107.clients.ibge.dtos.UFResponse;
import com.jeanbarcellos.project107.dtos.EnderecoRequest;
import com.jeanbarcellos.project107.entities.Endereco;
import com.jeanbarcellos.project107.entities.Estado;
import com.jeanbarcellos.project107.entities.Municipio;

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
