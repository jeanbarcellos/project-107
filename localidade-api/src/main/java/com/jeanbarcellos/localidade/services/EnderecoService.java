package com.jeanbarcellos.localidade.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.apache.commons.lang3.ObjectUtils;

import com.jeanbarcellos.core.validation.Validator;
import com.jeanbarcellos.localidade.dtos.EnderecoRequest;
import com.jeanbarcellos.localidade.dtos.EnderecoResponse;
import com.jeanbarcellos.localidade.entities.Endereco;
import com.jeanbarcellos.localidade.mapper.LocalidadeMapper;
import com.jeanbarcellos.localidade.repositories.EnderecoRepository;
import com.jeanbarcellos.localidade.repositories.MunicipioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class EnderecoService {

    public static final String MSG_ERROR_ENTITY_NOT_FOUND = "Endereço não encontrado para o ID '%s' informado.";

    @Inject
    Validator validator;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Transactional
    public void salvar(EnderecoRequest request) {
        validator.validarWithException(request);

        var endereco = new Endereco();
        if (request.getId() != null) {
            endereco = enderecoRepository.findByIdOptional(request.getId()).orElse(endereco);
        }

        LocalidadeMapper.copyProperties(endereco, request,
                municipioId -> this.municipioRepository.findById(municipioId));

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
