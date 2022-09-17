package com.jeanbarcellos.localidade.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.jeanbarcellos.localidade.clients.IBGELocalidadesClient;
import com.jeanbarcellos.localidade.dtos.EstadoResponse;
import com.jeanbarcellos.localidade.dtos.MunicipioResponse;
import com.jeanbarcellos.localidade.repositories.EstadoRepository;
import com.jeanbarcellos.localidade.repositories.MunicipioRepository;

import io.quarkus.panache.common.Sort;

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

}
