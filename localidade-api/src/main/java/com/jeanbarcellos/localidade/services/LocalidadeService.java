package com.jeanbarcellos.localidade.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.jeanbarcellos.localidade.clients.ibge.IBGELocalidadesClient;
import com.jeanbarcellos.localidade.clients.ibge.dtos.UFResponse;
import com.jeanbarcellos.localidade.dtos.BairroResponse;
import com.jeanbarcellos.localidade.dtos.EstadoResponse;
import com.jeanbarcellos.localidade.dtos.LogradouroResponse;
import com.jeanbarcellos.localidade.dtos.MunicipioResponse;
import com.jeanbarcellos.localidade.entities.Estado;
import com.jeanbarcellos.localidade.entities.Municipio;
import com.jeanbarcellos.localidade.mapper.LocalidadeMapper;
import com.jeanbarcellos.localidade.repositories.BairroRepository;
import com.jeanbarcellos.localidade.repositories.EstadoRepository;
import com.jeanbarcellos.localidade.repositories.LogradouroRepository;
import com.jeanbarcellos.localidade.repositories.MunicipioRepository;

import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class LocalidadeService {

    public static final String MSG_ERROR_ENTITY_NOT_FOUND_BY_ID = "%s não encontrado para o ID '%s' informado.";
    public static final String MSG_ERROR_ENTITY_NOT_FOUND_BY_INITIALS = "Estado não encontrado a partir da sigla informada.";

    private static final String FIELD_ID = "id";
    private static final String FIELD_NOME = "nome";
    private static final String FIELD_ESTADO = "estado";

    @RestClient
    @Inject
    IBGELocalidadesClient ibgeClient;

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

    // #region Sincronização

    @Transactional
    public void sincronizarEstados() {
        try {
            log.info("Sincronização dos Estados com o IBGE iniciada");

            List<UFResponse> estadosClient = this.ibgeClient.obterEstados(FIELD_NOME);

            for (UFResponse estadoClient : estadosClient) {
                if (this.estadoRepository.existsBy(FIELD_ID, estadoClient.getId())) {
                    continue;
                }

                var estado = LocalidadeMapper.toEstado(estadoClient);

                this.estadoRepository.persist(estado);

                log.info("Criando estado: " + estado.toString());
            }

            this.estadoRepository.flush();

            log.info("Sincronização dos Estados com o IBGE finalizada com sucesso");
        } catch (Exception e) {
            log.error("Sincronização dos Municipios com o IBGE interrompida por falha", e);
            throw e;
        }
    }

    @Transactional
    public void sincronizarMunicipios() {
        try {
            log.info("Sincronização dos Municipios com o IBGE iniciada");

            var municipiosClient = this.ibgeClient.obterMunicipios(FIELD_ID);

            for (com.jeanbarcellos.localidade.clients.ibge.dtos.MunicipioResponse municipioClient : municipiosClient) {
                if (this.municipioRepository.existsBy(FIELD_ID, municipioClient.getId())) {
                    continue;
                }

                var municipio = LocalidadeMapper.toMunicipio(municipioClient);

                var estado = this.estadoRepository.getReference(Estado.class, municipioClient.getUF().getId());
                municipio.setEstado(estado);

                this.municipioRepository.persist(municipio);

                log.info("Criando municipio: " + municipio.toString());
            }

            this.municipioRepository.flush();

            log.info("Sincronização dos Municipios com o IBGE finalizada com sucesso");
        } catch (Exception e) {
            log.error("Sincronização dos Municipios com o IBGE interrompida por falha", e);
            throw e;
        }
    }

    // #endregion
}
