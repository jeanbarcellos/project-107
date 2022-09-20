package com.jeanbarcellos.localidade.entities;

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
@Table(name = "bairro")
public class Bairro {

    @Id
    @GeneratedValue(generator = "bairro_id_seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "bairro_id_seq_generator", sequenceName = "bairro_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, columnDefinition = "BIGSERIAL")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "descricao", nullable = false)
    private String descricao;;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "bairro_municipio_id_fk"), nullable = false)
    private Municipio municipio;

}
