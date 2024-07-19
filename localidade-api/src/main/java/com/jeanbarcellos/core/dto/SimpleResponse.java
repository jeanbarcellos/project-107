package com.jeanbarcellos.core.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
@Builder
public class SimpleResponse {

    @Schema(description = "Mensagem genérica")
    private String message;
}
