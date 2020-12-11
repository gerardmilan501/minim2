package edu.upc.dsa.services;


import edu.upc.dsa.UsersManager;
import edu.upc.dsa.UsersManagerImpl;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.*;

@Api(value = "/partidas")
@Path("/partidas")
public class PartidasService {

    private UsersManager tm;

    public PartidasService() {
        this.tm = UsersManagerImpl.getInstance();
        if (tm.sizePartidas()==0) {
            this.tm.addPartida("1", "23456");
            this.tm.addPartida("2", "13456");
            this.tm.addPartida("3", "12456");
            this.tm.addPartida("4", "12356");

        }

    }

    @GET
    @ApiOperation(value = "Get all Partidas", notes = "Get all partidas from BBDD")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Partida.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPartidas() {

        List<Partida> partidas = this.tm.findAllPartidas();

        GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas) {};
        return Response.status(201).entity(entity).build()  ;

    }


    @GET
    @ApiOperation(value = "Get Partida", notes = "Get a partida from id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Partida found", response = Partida.class),
            @ApiResponse(code = 404, message = "Partida not found")
    })
    @Path("/{id_partida}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPartida(@PathParam("id_partida") String id_partida) {
        Partida p = this.tm.getPartida(id_partida);
        if (p == null) return Response.status(404).build();
        else  return Response.status(201).entity(p).build();
    }

    @DELETE
    @ApiOperation(value = "Delete a Partida", notes = "Delete a Partida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Partida found and deleted"),
            @ApiResponse(code = 404, message = "Partida not found")
    })
    @Path("/{id_partida}")
    public Response deletePartida(@PathParam("id_partida") String id_partida) {
        Partida p = this.tm.getPartida(id_partida);
        if (p == null) return Response.status(404).build();
        else this.tm.deletePartida(id_partida);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "Update Partida", notes = "Update Partida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Partida found"),
            @ApiResponse(code = 404, message = "Partida not found")
    })
    @Path("/")
    public Response updatePartida(Partida partida) {

        Partida p = this.tm.updatePartida(partida);

        if (p == null) return Response.status(404).build();

        return Response.status(201).build();
    }

    @POST
    @ApiOperation(value = "Create new Partida", notes = "Create Partida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful! Partida created"),
            @ApiResponse(code = 600, message = "Need to fill in id_partida field"),
            @ApiResponse(code = 601, message = "Need to fill in id_usuario field"),
            @ApiResponse(code = 250, message = "Partida already exists")

    })
    @Path("/registerp/{id_partida}/{id_usuario}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newPartida(@PathParam("id_partida") String id_partida, @PathParam("id_usuario") String id_usuario) {

        if (id_partida==null) return Response.status(600).build();
        if (id_usuario==null) return Response.status(601).build();

        if (this.tm.partidaExists(id_partida)) return Response.status(250).build();

        Partida pa = this.tm.addPartida(id_partida, id_usuario);
        return Response.status(201).build();
    }
                                                                                              //Adaptar para entrar a partida//
    @GET
    @ApiOperation(value = "A user tries to start a game", notes = "Login game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Login Game Successful!"),
            @ApiResponse(code = 601, message = "Need to fill in id_partida field"),
            @ApiResponse(code = 602, message = "Need to fill in id_usuario field"),
            @ApiResponse(code = 603, message = "Incorrect id_usuario"),
            @ApiResponse(code = 250, message = "Partida not exists")

    })
    @Path("/loginp/{id_partida}/{id_usuario}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response partidaLogin(@PathParam("id_partida") String id_partida, @PathParam("id_usuario") String id_usuario) {

        if (id_partida==null) return Response.status(601).build();
        if (id_usuario==null) return Response.status(602).build();
        if (!this.tm.partidaExists(id_partida)) return Response.status(250).build();
        if (!this.tm.checkUsuario(id_partida, id_usuario)) return Response.status(603).build();

        return Response.status(201).build();
    }

}