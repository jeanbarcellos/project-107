package com.jeanbarcellos.localidade.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "estado")
public class Estado {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "sigla", nullable = false, length = 2)
    private String sigla;

    @Column(name = "nome", nullable = false)
    private String nome;

    public Estado(Long id) {
        this.id = id;
    }

    public static Estado of(Long id) {
        return new Estado(id);
    }

}
