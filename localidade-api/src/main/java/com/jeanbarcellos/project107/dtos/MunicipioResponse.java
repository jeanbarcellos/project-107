package com.jeanbarcellos.project107.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.jeanbarcellos.project107.entities.Municipio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MunicipioResponse {

    private Long id;

    private String nome;

    public static MunicipioResponse of(Municipio entity) {
        return MunicipioResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .build();
    }

    public static List<MunicipioResponse> of(List<Municipio> entities) {
        return entities.stream().map(MunicipioResponse::of).collect(Collectors.toList());
    }
}
