package com.jeanbarcellos.localidade.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import com.jeanbarcellos.localidade.dtos.BairroResponse;
import com.jeanbarcellos.localidade.dtos.EstadoResponse;
import com.jeanbarcellos.localidade.dtos.LogradouroResponse;
import com.jeanbarcellos.localidade.dtos.MunicipioResponse;
import com.jeanbarcellos.localidade.entities.Estado;
import com.jeanbarcellos.localidade.entities.Municipio;
import com.jeanbarcellos.localidade.repositories.BairroRepository;
import com.jeanbarcellos.localidade.repositories.EstadoRepository;
import com.jeanbarcellos.localidade.repositories.LogradouroRepository;
import com.jeanbarcellos.localidade.repositories.MunicipioRepository;

import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class LocalidadeService {

    public static final String MSG_ERROR_ENTITY_NOT_FOUND_BY_ID = "%s não encontrado para o ID '%s' informado.";
    public static final String MSG_ERROR_ENTITY_NOT_FOUND_BY_INITIALS = "Estado não encontrado a partir da sigla informada.";

    private static final String FIELD_NOME = "nome";
    private static final String FIELD_ESTADO = "estado";

    @Inject
    EstadoRepository estadoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    BairroRepository bairroRepository;

    @Inject
    LogradouroRepository logradouroRepository;

    // #region Estados

    public List<EstadoResponse> obterEstados() {
        return EstadoResponse.of(this.estadoRepository.listAll(Sort.ascending(FIELD_NOME)));
    }

    public EstadoResponse obterEstado(Long id) {
        var estado = this.estadoRepository.findByIdOrThrow(id,
                () -> new NotFoundException(String.format(MSG_ERROR_ENTITY_NOT_FOUND_BY_ID, "Estado", id)));

        return EstadoResponse.of(estado);
    }

    public EstadoResponse obterEstadoPorSigla(String sigla) {
        var estado = this.estadoRepository.findFirstByOrTrhow("sigla", sigla.toUpperCase(),
                () -> new NotFoundException(MSG_ERROR_ENTITY_NOT_FOUND_BY_INITIALS));

        return EstadoResponse.of(estado);
    }

    // #endregion

    // #region Municípios

    public List<MunicipioResponse> obterMunicipios() {
        return MunicipioResponse.of(this.municipioRepository.listAll(Sort.ascending(FIELD_NOME)));
    }

    public List<MunicipioResponse> obterMunicipiosPorEstado(Long estadoId) {
        var municipio = this.municipioRepository.findBy(FIELD_ESTADO, Estado.of(estadoId), Sort.ascending(FIELD_NOME));

        return MunicipioResponse.of(municipio);
    }

    public MunicipioResponse obterMunicipio(Long id) {
        var municipio = this.municipioRepository.findByIdOrThrow(id,
                () -> new NotFoundException(String.format(MSG_ERROR_ENTITY_NOT_FOUND_BY_ID, "Município", id)));

        return MunicipioResponse.of(municipio);

    }

    // #endregion

    // #region Bairros

    public List<BairroResponse> obterBairros() {
        return BairroResponse.of(this.bairroRepository.listAll(Sort.ascending(FIELD_NOME)));
    }

    public List<BairroResponse> obterBairrosPorMunicipio(Long municipioId) {
        var bairro = this.bairroRepository.findBy(FIELD_ESTADO, Municipio.of(municipioId), Sort.ascending(FIELD_NOME));

        return BairroResponse.of(bairro);
    }

    public BairroResponse obterBairro(Long id) {
        var bairro = this.bairroRepository.findByIdOrThrow(id,
                () -> new NotFoundException(String.format(MSG_ERROR_ENTITY_NOT_FOUND_BY_ID, "Bairro", id)));

        return BairroResponse.of(bairro);

    }

    // #endregion

    // #region Logradouros

    public List<LogradouroResponse> obterLogradouros() {
        return LogradouroResponse.of(this.logradouroRepository.listAll(Sort.ascending(FIELD_NOME)));
    }

    public List<LogradouroResponse> obterLogradourosPorMunicipio(Long municipioId) {
        var logradouro = this.logradouroRepository.findBy(FIELD_ESTADO, Municipio.of(municipioId),
                Sort.ascending(FIELD_NOME));

        return LogradouroResponse.of(logradouro);
    }

    public LogradouroResponse obterLogradouro(Long id) {
        var logradouro = this.logradouroRepository.findByIdOrThrow(id,
                () -> new NotFoundException(String.format(MSG_ERROR_ENTITY_NOT_FOUND_BY_ID, "Logradouro", id)));

        return LogradouroResponse.of(logradouro);

    }

    // #endregion

}
