package com.jeanbarcellos.project107.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoRequest {

    @JsonIgnore
    @Schema(description = "ID do endereço. Caso ainda não tenha informar null", defaultValue = "null")
    private Long id;

    @NotBlank
    @Pattern(regexp = "\\d+", message = "deve contem somente números")
    @Size(min = 8, max = 8, message = "deve ter 8 caracteres")
    @Schema(description = "CEP somente números", maxLength = 8)
    private String cep;

    @NotNull
    @Schema(description = "ID/Código do município segundo IBGE")
    private Long municipioId;

    @NotBlank
    @Size(max = 96)
    @Schema(description = "Bairro")
    private String bairro;

    @NotBlank
    @Size(max = 96)
    @Schema(description = "Logradouro")
    private String logradouro;

    @Schema(description = "Número")
    private Integer numero;

    @Size(max = 96)
    @Schema(description = "Complemento")
    private String complemento;

}
