package com.jeanbarcellos.project107.clients.viacep.dtos;

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

    private String uf;

    private Long ibge;

    private String localidade;

    private String bairro;

    private String logradouro;

    private String complemento;

    private String unidade;

    private Long gia;

    private Integer ddd;

    private Long siafi;

    public String getCep() {
        return StringUtils.isNotEmpty(this.cep) ? this.cep.replaceAll("\\D", "") : null;
    }

    public String getLogradouro() {
        return StringUtils.isNotEmpty(this.logradouro) ? this.logradouro : null;
    }

    public String getUnidade() {
        return StringUtils.isNotEmpty(this.unidade) ? this.unidade : null;
    }

    public String getComplemento() {
        return StringUtils.isNotEmpty(this.complemento) ? this.complemento : null;
    }

    public String getBairro() {
        return StringUtils.isNotEmpty(this.bairro) ? this.bairro : null;
    }

}
