package com.jeanbarcellos.core.dto;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;

/**
 * Response para Erro com lista de detalhes
 *
 * @author Jean Silva de Barcellos
 */
@Getter
public class ErrorResponse {

    @Schema(description = "Descrição genérica do erro")
    private String message;

    @Schema(description = "Detalhes do erro")
    @JsonInclude(Include.NON_NULL)
    private List<String> errors;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public boolean hasErros() {
        return !this.errors.isEmpty();
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse of(String message, List<String> errors) {
        return new ErrorResponse(message, errors);
    }

}
