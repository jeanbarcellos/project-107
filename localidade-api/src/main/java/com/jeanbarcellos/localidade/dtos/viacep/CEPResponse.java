package com.jeanbarcellos.localidade.dtos.viacep;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CEPResponse {

    private String cep;

    private String logradouro;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    private Long ibge;

    private Long gia;

    private Integer ddd;

    private Long siafi;

    public String getCep() {
        return this.cep.replaceAll("\\D", "");
    }

    public String getLogradouro() {
        return StringUtils.isNotEmpty(this.logradouro) ? this.logradouro : null;
    }

    public String getComplemento() {
        return StringUtils.isNotEmpty(this.complemento) ? this.complemento : null;
    }

    public String getBairro() {
        return StringUtils.isNotEmpty(this.bairro) ? this.bairro : null;
    }

}
