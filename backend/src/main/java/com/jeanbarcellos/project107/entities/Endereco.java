package com.jeanbarcellos.project107.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(generator = "endereco_id_seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "endereco_id_seq_generator", sequenceName = "endereco_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, columnDefinition = "BIGSERIAL")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "endereco_municipio_id_fk"), nullable = false)
    private Municipio municipio;

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Column(name = "numero", nullable = true)
    private Integer numero;

    @Column(name = "complemento", nullable = true)
    private String complemento;

}
