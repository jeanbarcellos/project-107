package com.jeanbarcellos.localidade.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.jeanbarcellos.localidade.entities.Logradouro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogradouroResponse {

    private Long id;

    private String nome;

    private String descricao;

    public static LogradouroResponse of(Logradouro entity) {
        return LogradouroResponse.builder()
                .id(entity.getId())
                .descricao(entity.getDescricao())
                .build();
    }

    public static List<LogradouroResponse> of(List<Logradouro> entities) {
        return entities.stream().map(LogradouroResponse::of).collect(Collectors.toList());
    }
}
