package com.jeanbarcellos.localidade.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.jeanbarcellos.localidade.clients.IBGELocalidadesClient;

@Path("/ibge/localidades")
@Tag(name = "IBGE Localidades", description = "Endpoits para obter localidades do IBGE")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class IBGELocalidadesResource {

    @RestClient
    @Inject
    IBGELocalidadesClient client;

    @GET
    @Path("/estados")
    public Response obterEstados() {
        var result = this.client.obterEstados("nome");

        return Response.ok(result).build();
    }

    @GET
    @Path("/estados/{id}")
    public Response obterEstado(@PathParam String id) {

        var result = this.client.obterEstado(id);

        return Response.ok(result).build();
    }

    @GET
    @Path("/estados/{id}/municipios")
    public Response obterEstadoPorMunicipio(@PathParam String id) {

        var result = this.client.obterMunicipiosPorEstadoId(id);

        return Response.ok(result).build();
    }

    @GET
    @Path("/municipios/{id}")
    public Response obterMunicipio(@PathParam String id) {

        var result = this.client.obterMunicipio(id);

        return Response.ok(result).build();
    }

}
