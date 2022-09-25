package com.jeanbarcellos.localidade.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.jeanbarcellos.localidade.entities.Endereco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoResponse {

    private Long id;

    private String cep;

    private MunicipioResponse municipio;

    private String bairro;

    private String logradouro;

    private String numero;

    private String complemento;

    public static EnderecoResponse of(Endereco entity) {
        return EnderecoResponse.builder()
                .id(entity.getId())
                .cep(entity.getCep())
                .municipio(MunicipioResponse.of(entity.getMunicipio()))
                .bairro(entity.getBairro())
                .logradouro(entity.getLogradouro())
                .complemento(entity.getComplemento())
                .build();
    }

    public static List<EnderecoResponse> of(List<Endereco> entities) {
        return entities.stream().map(EnderecoResponse::of).collect(Collectors.toList());
    }

}
