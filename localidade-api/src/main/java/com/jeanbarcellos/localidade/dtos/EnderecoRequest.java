package com.jeanbarcellos.localidade.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoRequest {

    @Schema(description = "ID do endereço. Caso ainda não tenha informar null", defaultValue = "null")
    private Long id;

    @NotBlank
    @Schema(description = "CEP somente números", maxLength = 8)
    private String cep;

    @NotBlank
    @Schema(description = "Logradouro")
    private String logradouro;

    @Schema(description = "Número")
    private Integer numero;

    @Schema(description = "Complemento")
    private String complemento;

    @NotBlank
    @Schema(description = "Bairro")
    private String bairro;

    @NotNull
    @Schema(description = "ID/Còdigo do município segundo IBGE")
    private Long municipioId;

}
