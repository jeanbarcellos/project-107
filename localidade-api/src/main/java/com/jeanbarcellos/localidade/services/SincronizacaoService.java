package com.jeanbarcellos.localidade.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.jeanbarcellos.localidade.clients.ibge.IBGELocalidadesClient;
import com.jeanbarcellos.localidade.clients.ibge.dtos.UFResponse;
import com.jeanbarcellos.localidade.entities.Estado;
import com.jeanbarcellos.localidade.mapper.LocalidadeMapper;
import com.jeanbarcellos.localidade.repositories.EstadoRepository;
import com.jeanbarcellos.localidade.repositories.MunicipioRepository;

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

}
