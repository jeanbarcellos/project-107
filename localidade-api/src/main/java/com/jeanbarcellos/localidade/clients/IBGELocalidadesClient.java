package com.jeanbarcellos.localidade.clients;

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

import com.jeanbarcellos.localidade.dtos.ibge.MunicipioResponse;
import com.jeanbarcellos.localidade.dtos.ibge.UFResponse;

@RegisterRestClient(configKey = "ibge-localidades-api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IBGELocalidadesClient {

    @GET
    @Path("/estados")
    public List<UFResponse> obterEstados(@DefaultValue("nome") @QueryParam("orderBy") String orderBy);

    @GET
    @Path("/estados/{id}")
    public UFResponse obterEstado(@PathParam String id);

    @GET
    @Path("/estados/{id}/municipios")
    public List<MunicipioResponse> obterMunicipiosPorEstadoId(@PathParam("id") String id);

    @GET
    @Path("/municipios")
    public List<MunicipioResponse> obterMunicipios(@DefaultValue("if") @QueryParam("orderBy") String orderBy);

    @GET
    @Path("/municipios/{id}")
    public MunicipioResponse obterMunicipio(@PathParam String id);

}
