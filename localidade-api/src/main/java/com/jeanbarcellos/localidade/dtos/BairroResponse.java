package com.jeanbarcellos.localidade.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.jeanbarcellos.localidade.entities.Bairro;

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

    private String nome;

    public static BairroResponse of(Bairro entity) {
        return BairroResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .build();
    }

    public static List<BairroResponse> of(List<Bairro> entities) {
        return entities.stream().map(BairroResponse::of).collect(Collectors.toList());
    }
}
