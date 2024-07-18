package com.jeanbarcellos.project107.resources;

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

import com.jeanbarcellos.project107.clients.viacep.ViaCEPClient;

@Path("/viacep")
@Tag(name = "Integração ViaCEP", description = "Acesso a API ViaCEP")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ViaCEPResource {

    @RestClient
    @Inject
    protected ViaCEPClient client;

    @GET
    @Path("/{cep}")
    public Response obterCep(@PathParam String cep) {
        var response = this.client.obterCep(cep);

        return Response.ok(response).build();
    }

}
