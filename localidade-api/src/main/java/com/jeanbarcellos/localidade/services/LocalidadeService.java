package com.jeanbarcellos.localidade.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.jeanbarcellos.localidade.clients.IBGELocalidadesClient;
import com.jeanbarcellos.localidade.dtos.EstadoResponse;
import com.jeanbarcellos.localidade.dtos.MunicipioResponse;
import com.jeanbarcellos.localidade.dtos.ibge.UFResponse;
import com.jeanbarcellos.localidade.mapper.LocalidadeMapper;
import com.jeanbarcellos.localidade.repositories.EstadoRepository;
import com.jeanbarcellos.localidade.repositories.MunicipioRepository;

import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class LocalidadeService {

    @RestClient
    @Inject
    IBGELocalidadesClient ibgeClient;

    @Inject
    EstadoRepository estadoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    // #region Estados

    public List<EstadoResponse> obterEstados() {
        return EstadoResponse.of(this.estadoRepository.listAll(Sort.ascending("nome")));
    }

    public EstadoResponse obterEstado(Long id) {
        var estado = this.estadoRepository.findByIdOrThrow(id,
                () -> new NotFoundException("Estado não encontrado a partir do ID informado"));

        return EstadoResponse.of(estado);
    }

    public EstadoResponse obterEstadoPorSigla(String sigla) {
        var estado = this.estadoRepository.findFirstByOrTrhow("sigla", sigla.toUpperCase(),
                () -> new NotFoundException("Estado não encontrado a partir da sigla informada"));

        return EstadoResponse.of(estado);
    }

    // #endregion

    // #region Municípios

    public List<MunicipioResponse> obterMunicipios() {
        return MunicipioResponse.of(this.municipioRepository.listAll(Sort.ascending("nome")));
    }

    public List<MunicipioResponse> obterMunicipiosPorEstado(Long estadoId) {
        return MunicipioResponse.of(this.municipioRepository.findBy("estado", estadoId, Sort.ascending("nome")));
    }

    public MunicipioResponse obterMunicipio(Long id) {
        var municipio = this.municipioRepository.findByIdOrThrow(id,
                () -> new NotFoundException("Município não encontrado a partir do ID informado"));

        return MunicipioResponse.of(municipio);

    }

    // #endregion

    // #region Bairros

    public void obterBairros() {

    }

    public void obterBairrosPorMunicipio(Long municipioId) {

    }

    public void obterBairroPorId(Long id) {

    }

    // #endregion

    // #region Logradouros

    public void obterLogradouros() {

    }

    public void obterLogradourosPorBairro(Long bairroId) {

    }

    public void obterLogradourosPorMunicipio(Long municipioId) {

    }

    public void obterLogradouroPorId(Long id) {

    }

    // #endregion

    // #region Sincronização

    @Transactional
    public void sincronizarEstados() {
        try {
            log.info("Sincronização dos Estados com o IBGE iniciada");

            List<UFResponse> estadosClient = this.ibgeClient.obterEstados("nome");

            for (UFResponse estadoClient : estadosClient) {
                if (this.estadoRepository.existsBy("id", estadoClient.getId())) {
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

            var municipiosClient = this.ibgeClient.obterMunicipios("id");

            for (com.jeanbarcellos.localidade.dtos.ibge.MunicipioResponse municipioClient : municipiosClient) {
                if (this.municipioRepository.existsBy("id", municipioClient.getId())) {
                    continue;
                }

                var municipio = LocalidadeMapper.toMunicipio(municipioClient);

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
