package com.jeanbarcellos.localidade.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.jeanbarcellos.localidade.dtos.EnderecoRequest;
import com.jeanbarcellos.localidade.services.EnderecoService;

@Path("/enderecos")
@Tag(name = "Endereços", description = "Manutenção de endereços")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class EnderecoResource {

    @Inject
    EnderecoService service;

    @POST
    @Path("/")
    @Operation(summary = "Salvar endereco", description = "Salva um endereco")
    public Response salvar(@RequestBody EnderecoRequest request) {
        service.salvar(request);

        return Response.ok().status(Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obter endereco por ID", description = "Retorna os detalhes de um endereço pelo seu ID")
    public Response obterPorId(@PathParam Long id) {
        return Response.ok(service.obterPorId(id)).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Obter enderecos", description = "Retorna todos os enderecos cadastrados")
    public Response obterTodos() {
        return Response.ok(service.obterEnderecos()).build();
    }

}
