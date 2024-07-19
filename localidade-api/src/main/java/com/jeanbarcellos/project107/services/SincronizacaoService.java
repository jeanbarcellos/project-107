package com.jeanbarcellos.project107.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.jeanbarcellos.core.dto.SimpleResponse;
import com.jeanbarcellos.project107.clients.ibge.IBGELocalidadesClient;
import com.jeanbarcellos.project107.clients.ibge.dtos.MunicipioResponse;
import com.jeanbarcellos.project107.clients.ibge.dtos.UFResponse;
import com.jeanbarcellos.project107.entities.Estado;
import com.jeanbarcellos.project107.mapper.LocalidadeMapper;
import com.jeanbarcellos.project107.repositories.EstadoRepository;
import com.jeanbarcellos.project107.repositories.MunicipioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class SincronizacaoService {

    public static final String MSG_ERROR_ENTITY_NOT_FOUND_BY_ID = "%s não encontrado para o ID '%s' informado.";
    public static final String MSG_ERROR_ENTITY_NOT_FOUND_BY_INITIALS = "Estado não encontrado a partir da sigla informada.";

    private static final String FIELD_ID = "id";
    private static final String FIELD_NOME = "nome";

    @RestClient
    @Inject
    IBGELocalidadesClient ibgeClient;

    @Inject
    EstadoRepository estadoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    LocalidadeMapper localidadeMapper;

    @Transactional
    public SimpleResponse sincronizarEstados() {
        try {
            log.info("Sincronização dos Estados com o IBGE iniciada");

            var ufsResponse = this.ibgeClient.obterEstados(FIELD_NOME);

            for (UFResponse ufResponse : ufsResponse) {
                salvarEstado(ufResponse);
            }

            this.estadoRepository.flush();

            var message = "Sincronização dos Estados com o IBGE finalizada com sucesso";

            log.info(message);

            return SimpleResponse.of(message);
        } catch (Exception e) {
            log.error("Sincronização dos Municipios com o IBGE interrompida por falha", e);
            throw e;
        }
    }

    private void salvarEstado(UFResponse ufResponse) {
        if (this.estadoRepository.existsBy(FIELD_ID, ufResponse.getId())) {
            return;
        }

        var estado = this.localidadeMapper.toEstado(ufResponse);

        this.estadoRepository.persist(estado);

        log.info("Criando estado: " + estado.toString());
    }

    @Transactional
    public SimpleResponse sincronizarMunicipios() {
        try {
            log.info("Sincronização dos Municipios com o IBGE iniciada");

            var municipiosResponse = this.ibgeClient.obterMunicipios(FIELD_ID);

            for (MunicipioResponse municipioResponse : municipiosResponse) {
                salvarMunicipio(municipioResponse);
            }

            this.municipioRepository.flush();

            var message = "Sincronização dos Municipios com o IBGE finalizada com sucesso";

            log.info(message);

            return SimpleResponse.of(message);
        } catch (Exception e) {
            log.error("Sincronização dos Municipios com o IBGE interrompida por falha", e);
            throw e;
        }
    }

    private void salvarMunicipio(MunicipioResponse municipioResponse) {
        if (this.municipioRepository.existsBy(FIELD_ID, municipioResponse.getId())) {
            return;
        }

        var municipio = this.localidadeMapper.toMunicipio(municipioResponse);

        var estado = this.estadoRepository.getReference(Estado.class, municipioResponse.getUF().getId());
        municipio.setEstado(estado);

        this.municipioRepository.persist(municipio);

        log.info("Criando municipio: " + municipio.toString());
    }

    @Transactional
    public SimpleResponse sincronizarMunicipiosPorEstadoId(String estadoId) {
        try {
            log.info("Sincronização dos Municipios do estado '{}' com o IBGE iniciada", estadoId);

            var municipiosResponse = this.ibgeClient.obterMunicipiosPorEstadoId(estadoId);

            for (MunicipioResponse municipioResponse : municipiosResponse) {
                salvarMunicipio(municipioResponse);
            }

            this.municipioRepository.flush();

            var message = String.format("Sincronização dos Municipios estado '%s' com o IBGE finalizada com sucesso", estadoId);

            log.info(message);

            return SimpleResponse.of(message);
        } catch (Exception e) {
            log.error(String.format("Sincronização dos Municipios estado '%s' com o IBGE interrompida por falha",
                    estadoId), e);
            throw e;
        }
    }

}
