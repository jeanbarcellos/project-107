package com.jeanbarcellos.localidade.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.jeanbarcellos.localidade.clients.IBGELocalidadesClient;

@Path("/ibge/localidades")
@Tag(name = "Integração IBGE Localidades", description = "Acesso a API de Localidades do IBGE")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class IBGELocalidadesResource {

    @RestClient
    @Inject
    IBGELocalidadesClient client;

    @GET
    @Path("/estados")
    @Operation(summary = "Obter UFs", description = "Obtém o conjunto de Unidades da Federação do Brasil")
    public Response obterEstados() {
        var result = this.client.obterEstados("nome");

        return Response.ok(result).build();
    }

    @GET
    @Path("/estados/{id}")
    @Operation(summary = "Obter UFs por identificador", description = "Obtém o conjunto de Unidades da Federação do Brasil a partir do identificador")
    public Response obterEstado(@PathParam String id) {

        var result = this.client.obterEstado(id);

        return Response.ok(result).build();
    }

    @GET
    @Path("/estados/{id}/municipios")
    @Operation(summary = "Obter Municípios por UF", description = "Obtém o conjunto de municípios do Brasil a partir do identificador das Unidades da Federação")
    public Response obterEstadoPorMunicipio(@PathParam String id) {

        var result = this.client.obterMunicipiosPorEstadoId(id);

        return Response.ok(result).build();
    }

    @GET
    @Path("/municipios/{id}")
    @Operation(summary = "Obter Município por identificador", description = "Obtém o conjunto de municípios do Brasil a partir dos respectivos identificadores")
    public Response obterMunicipio(@PathParam String id) {

        var result = this.client.obterMunicipio(id);

        return Response.ok(result).build();
    }

}
