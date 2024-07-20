package com.jeanbarcellos.project107.services;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.apache.commons.lang3.ObjectUtils;

import com.jeanbarcellos.core.validation.Validator;
import com.jeanbarcellos.project107.dtos.EnderecoRequest;
import com.jeanbarcellos.project107.dtos.EnderecoResponse;
import com.jeanbarcellos.project107.entities.Endereco;
import com.jeanbarcellos.project107.mapper.LocalidadeMapper;
import com.jeanbarcellos.project107.repositories.EnderecoRepository;
import com.jeanbarcellos.project107.repositories.MunicipioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class EnderecoService {

    public static final String MSG_ERROR_ENTITY_NOT_FOUND = "Endereço não encontrado para o ID '%s' informado.";
    public static final String MSG_ERROR_ENTITY_NOT_FOUND_BY_ID = "%s não encontrado para o ID '%s' informado.";

    @Inject
    Validator validator;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    LocalidadeMapper localidadeMapper;

    @PostConstruct
    public void init() {
        this.localidadeMapper.setProviderMunicipio(
                municipioId -> this.municipioRepository.findByIdOrThrow(municipioId,
                        () -> new NotFoundException(
                                String.format(MSG_ERROR_ENTITY_NOT_FOUND_BY_ID, "Município", municipioId))));
    }

    @Transactional
    public void salvar(EnderecoRequest request) {
        validator.validarWithException(request);

        var endereco = new Endereco();
        if (request.getId() != null) {
            endereco = enderecoRepository.findByIdOptional(request.getId()).orElse(endereco);
        }

        this.localidadeMapper.copyProperties(endereco, request);

        log.info("Persistindo endereco {}", endereco);

        if (ObjectUtils.isEmpty(request.getId())) {
            this.enderecoRepository.persist(endereco);
        }

        this.enderecoRepository.flush();
    }

    public EnderecoResponse obterPorId(Long id) {
        var endereco = enderecoRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_ERROR_ENTITY_NOT_FOUND, id)));

        return EnderecoResponse.of(endereco);
    }

}
