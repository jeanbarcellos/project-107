package com.jeanbarcellos.localidade.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.jeanbarcellos.localidade.entities.Estado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoResponse {

    private Long id;

    private String sigla;

    private String nome;

    public static EstadoResponse of(Estado entity) {
        return EstadoResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .sigla(entity.getSigla())
                .build();
    }

    public static List<EstadoResponse> of(List<Estado> entities) {
        return entities.stream().map(EstadoResponse::of).collect(Collectors.toList());
    }
}
