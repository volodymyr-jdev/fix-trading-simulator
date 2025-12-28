package com.helesto.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.helesto.dto.MessageDto;
import com.helesto.dto.SessionDto;
import com.helesto.service.MessageService;
import com.helesto.service.SessionService;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.ConfigError;

@RequestScoped
@Tag(name = "Session control")
@Path("/session")
public class SessionRest {

    private static final Logger LOG = LoggerFactory.getLogger(SessionRest.class.getName());

    @Inject
    SessionService sessionService;

    @Inject
    MessageService messageService;

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Operation(summary = "Return session information")
    @APIResponse(responseCode = "200", description = "Read session information", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SessionDto.class)) })
    public Response sessionGet() {

        LOG.info("sessionGet");

        SessionDto sessionDto = sessionService.sessionGet();

        Jsonb jsonb = JsonbBuilder.create();
        String jsonString = jsonb.toJson(sessionDto);

        LOG.debug("Session + GET - response: " + jsonString);

        return Response.status(Response.Status.OK).entity(sessionDto).build();
    }

    @Path("/start-initiator")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Operation(summary = "Start initiator and make logon")
    @APIResponse(responseCode = "200", description = "Initiator started", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SessionDto.class)) })
    public Response startInitiator() throws ConfigError {

        LOG.debug("Session + POST - start initiator");

        SessionDto sessionDto = sessionService.startInitiator();

        Jsonb jsonb = JsonbBuilder.create();
        String jsonString = jsonb.toJson(sessionDto);

        LOG.debug("Session + POST - start initiator - response: " + jsonString);

        return Response.status(Response.Status.OK).entity(sessionDto).build();

    }

    @Path("/stop-initiator")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Operation(summary = "Logout and stop initiator")
    @APIResponse(responseCode = "200", description = "Initiator stopped", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SessionDto.class)) })
    public Response stopInitiator() throws ConfigError {

        LOG.debug("Session + POST - stop initiator");

        SessionDto sessionDto = sessionService.stopInitiator();

        Jsonb jsonb = JsonbBuilder.create();
        String jsonString = jsonb.toJson(sessionDto);

        LOG.debug("Session + POST - stop initiator - response: " + jsonString);

        return Response.status(Response.Status.OK).entity(sessionDto).build();

    }

    @Path("/logout")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Operation(summary = "Logout")
    @APIResponse(responseCode = "200", description = "Logout done", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SessionDto.class)) })
    public Response logout() throws ConfigError {

        LOG.debug("Session + POST - logout");

        SessionDto sessionDto = sessionService.logout();

        Jsonb jsonb = JsonbBuilder.create();
        String jsonString = jsonb.toJson(sessionDto);

        LOG.debug("Session + POST - logout - response: " + jsonString);

        return Response.status(Response.Status.OK).entity(sessionDto).build();

    }

    @Path("/messages")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Operation(summary = "Return Messages sent from current session")
    @APIResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto[].class)) })
    public Response messageGet() throws ConfigError {

        LOG.info("messageGet");

        MessageDto[] messageDto = messageService.messageGet();

        Jsonb jsonb = JsonbBuilder.create();
        String jsonString = jsonb.toJson(messageDto);

        LOG.debug("Session + GET - response: " + jsonString);

        return Response.status(Response.Status.OK).entity(messageDto).build();
    }

}
