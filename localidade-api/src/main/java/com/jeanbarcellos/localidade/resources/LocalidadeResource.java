package com.jeanbarcellos.localidade.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.jeanbarcellos.localidade.services.LocalidadeService;

@Path("/localidades")
@Tag(name = "Localidades", description = "Manutenção de localidades do Brasil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class LocalidadeResource {

    @Inject
    LocalidadeService service;

    @GET
    @Path("/estados")
    @Operation(summary = "Obter estados", description = "Retorna todos os estados do Brasil por ordem alfabética")
    public Response obterEstados() {
        return Response.ok(this.service.obterEstados()).build();
    }

    @GET
    @Path("/estados/{id}")
    @Operation(summary = "Obter estado por ID", description = "Retorna os detalhes de um estado a partir do seu ID")
    public Response obterEstadoPorId(@PathParam("id") Long id) {
        return Response.ok(this.service.obterEstado(id)).build();
    }

    @GET
    @Path("/estados/sigla/{sigla}")
    @Operation(summary = "Obter estado por sigla", description = "Retorna os detalhes de um estado a partir da sua sigla")
    public Response obterEstadoPorId(@PathParam("sigla") String sigla) {
        return Response.ok(this.service.obterEstadoPorSigla(sigla)).build();
    }

    @GET
    @Path("/municipios")
    @Operation(summary = "Obter municipios", description = "Retorna todos os municipios do Brasil por ordem alfabética")
    public Response obterMunicipios() {
        return Response.ok(this.service.obterMunicipios()).build();
    }

    @GET
    @Path("/municipios/{id}")
    @Operation(summary = "Obter municipio por ID", description = "Retorna os detalhes de um municipio a partir do seu ID")
    public Response obterMunicipioPorId(@PathParam("id") Long id) {
        return Response.ok(this.service.obterMunicipio(id)).build();
    }

    @GET
    @Path("/estados/{id}/municipios")
    @Operation(summary = "Obter todos municipios de um estado", description = "Retorna todos os municípios de um estado informado")
    public Response obterMunicipioPorEstado(@PathParam("id") Long id) {
        return Response.ok(this.service.obterMunicipiosPorEstado(id)).build();
    }

}
