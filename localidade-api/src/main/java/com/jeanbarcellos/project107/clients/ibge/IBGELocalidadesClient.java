package com.jeanbarcellos.project107.clients.ibge;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import com.jeanbarcellos.project107.clients.ibge.dtos.MunicipioResponse;
import com.jeanbarcellos.project107.clients.ibge.dtos.UFResponse;

@RegisterRestClient(configKey = "ibge-localidades-api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IBGELocalidadesClient {

    @GET
    @Path("/estados")
    public List<UFResponse> obterEstados(
            @DefaultValue(UFResponse.LABEL_ID) @QueryParam("orderBy") String orderBy);

    @GET
    @Path("/estados/{id}")
    public UFResponse obterEstado(@PathParam String id);

    @GET
    @Path("/estados/{id}/municipios")
    public List<MunicipioResponse> obterMunicipiosPorEstadoId(@PathParam("id") String id);

    @GET
    @Path("/municipios")
    public List<MunicipioResponse> obterMunicipios(
            @DefaultValue(MunicipioResponse.LABEL_ID) @QueryParam("orderBy") String orderBy);

    @GET
    @Path("/municipios/{id}")
    public MunicipioResponse obterMunicipio(@PathParam String id);

}
