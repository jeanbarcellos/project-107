package com.jeanbarcellos.project107.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.jeanbarcellos.project107.entities.Bairro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BairroResponse {

    private Long id;

    private String descricao;

    public static BairroResponse of(Bairro entity) {
        return BairroResponse.builder()
                .id(entity.getId())
                .descricao(entity.getDescricao())
                .build();
    }

    public static List<BairroResponse> of(List<Bairro> entities) {
        return entities.stream().map(BairroResponse::of).collect(Collectors.toList());
    }
}
