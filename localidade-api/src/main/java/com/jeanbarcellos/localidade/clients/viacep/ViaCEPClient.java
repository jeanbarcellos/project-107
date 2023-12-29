package com.jeanbarcellos.localidade.clients.viacep;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.jeanbarcellos.localidade.clients.viacep.dtos.CEPResponse;

@RegisterRestClient(configKey = "viacep-api")
public interface ViaCEPClient {

    @GET
    @Path("/{cep}/json")
    public CEPResponse obterCep(@PathParam String cep);

}
