package com.jeanbarcellos.project107.resources;

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
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.jeanbarcellos.project107.services.SincronizacaoService;

@Path("/sincronizacao")
@Tag(name = "Sincronização", description = "Sincronização da base local de localidades com o IBGE")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SincronizacaoResource {

    @Inject
    protected SincronizacaoService service;

    @GET
    @Path("/estados")
    @Operation(summary = "Sincronizar estados com o IBGE", description = "Sincroniza a base local de estados com o IBGE")
    public Response sincronizarEstados() {
        return Response.ok(this.service.sincronizarEstados()).build();
    }

    @GET
    @Path("/estados/{id}/municipios")
    @Operation(summary = "Sincronizar municípios por estado com o IBGE", description = "Sincroniza a base local de municipios de um estado com o IBGE")
    public Response sincronizarMunicipiosPorEstadoId(@PathParam String id) {
        return Response.ok(this.service.sincronizarMunicipiosPorEstadoId(id)).build();
    }

    @GET
    @Path("/municipios")
    @Operation(summary = "Sincronizar municípios com o IBGE", description = "Sincroniza a base local de municipios com o IBGE")
    public Response sincronizarMunicipios() {
        return Response.ok(this.service.sincronizarMunicipios()).build();
    }

}
