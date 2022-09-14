package com.jeanbarcellos.localidade.clients;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.jeanbarcellos.localidade.dtos.viacep.CEPResponse;

@RegisterRestClient(configKey = "viacep-api")
public interface ViaCEPClient {

    @GET
    @Path("/{cep}/json")
    public CEPResponse getCep(@PathParam String cep);

}
