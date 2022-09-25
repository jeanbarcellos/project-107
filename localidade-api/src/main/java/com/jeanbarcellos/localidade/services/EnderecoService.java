package com.jeanbarcellos.localidade.services;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.apache.commons.lang3.ObjectUtils;

import com.jeanbarcellos.Constants;
import com.jeanbarcellos.core.Validator;
import com.jeanbarcellos.core.exception.ValidationException;
import com.jeanbarcellos.localidade.dtos.EnderecoRequest;
import com.jeanbarcellos.localidade.dtos.EnderecoResponse;
import com.jeanbarcellos.localidade.entities.Bairro;
import com.jeanbarcellos.localidade.entities.Endereco;
import com.jeanbarcellos.localidade.entities.Logradouro;
import com.jeanbarcellos.localidade.entities.Municipio;
import com.jeanbarcellos.localidade.mapper.LocalidadeMapper;
import com.jeanbarcellos.localidade.repositories.BairroRepository;
import com.jeanbarcellos.localidade.repositories.EnderecoRepository;
import com.jeanbarcellos.localidade.repositories.LogradouroRepository;
import com.jeanbarcellos.localidade.repositories.MunicipioRepository;

import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class EnderecoService {

    public static final String MSG_ERROR_ENTITY_NOT_FOUND = "Endereço não encontrado para o ID '%s' informado.";

    private static final String FIELD_ID = "id";
    private static final String FIELD_NOME = "nome";

    @Inject
    Validator validator;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    BairroRepository bairroRepository;

    @Inject
    LogradouroRepository logradouroRepository;

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
                municipioId -> this.municipioRepository.findByIdOrThrow(municipioId,
                        () -> new ValidationException(Constants.MSG_ERROR_VALIDATION,
                                Arrays.asList("O campo 'municpioId' possui ID de um município inexistente"))));

        log.info("Persistindo endereco {}", endereco);

        salvarBairro(endereco.getMunicipio(), endereco.getBairro());
        salvarLogradouro(endereco.getMunicipio(), endereco.getLogradouro());

        if (ObjectUtils.isEmpty(request.getId())) {
            this.enderecoRepository.persist(endereco);
        }

        this.enderecoRepository.flush();
    }

    private Bairro salvarBairro(Municipio municipio, String nome) {
        Bairro bairro = this.bairroRepository.findFirstBy(FIELD_NOME, nome);

        if (bairro != null) {
            return bairro;
        }

        bairro = new Bairro(municipio, nome);

        this.bairroRepository.persist(bairro);

        this.bairroRepository.flush();

        return bairro;
    }

    private Logradouro salvarLogradouro(Municipio municipio, String nome) {
        Logradouro logradouro = this.logradouroRepository.findFirstBy(FIELD_NOME, nome);

        if (logradouro != null) {
            return logradouro;
        }

        logradouro = new Logradouro(municipio, nome);

        this.logradouroRepository.persist(logradouro);

        this.logradouroRepository.flush();

        return logradouro;
    }

    public EnderecoResponse obterPorId(Long id) {
        var endereco = enderecoRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_ERROR_ENTITY_NOT_FOUND, id)));

        return EnderecoResponse.of(endereco);
    }

    public List<EnderecoResponse> obterEnderecos() {
        return EnderecoResponse.of(this.enderecoRepository.listAll(Sort.ascending(FIELD_ID)));
    }

}
