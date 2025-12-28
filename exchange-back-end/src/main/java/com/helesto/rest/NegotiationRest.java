package com.helesto.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.helesto.core.Exchange;
import com.helesto.dto.NegotiationDto;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.ConfigError;
import quickfix.SessionNotFound;

@Path("/negotiation")
@Tag(name = "Negotiation configuration")
@RequestScoped
public class NegotiationRest {

        private static final Logger LOG = LoggerFactory.getLogger(NegotiationRest.class.getName());

        @Inject
        Exchange exchange;

        @GET
        @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Operation(summary = "Negotiation configuration")
        @APIResponse(responseCode = "200", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = NegotiationDto.class)) })
        public Response get() throws ConfigError {

                LOG.debug("Negotiation - GET");

                NegotiationDto response = new NegotiationDto(exchange.isAutomaticTrade(),
                                exchange.getAutomaticTradeSeconds());

                Jsonb jsonb = JsonbBuilder.create();
                String jsonString = jsonb.toJson(response);

                LOG.debug("Negotiation - GET - response: " + jsonString);

                return Response.status(Response.Status.OK).entity(response).build();

        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Operation(summary = "Update negotiation configuration")
        @APIResponse(responseCode = "200", description = "Order", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = NegotiationDto.class)) })
        public Response put(NegotiationDto negotiationDto) throws SessionNotFound {

                Jsonb jsonb = JsonbBuilder.create();
                String jsonString = jsonb.toJson(negotiationDto);

                exchange.setAutomaticTrade(negotiationDto.isAutomaticTrade());

                exchange.setAutomaticTradeSeconds(negotiationDto.getAutomaticTradeSeconds());

                LOG.debug("Negotiation - PUT - request \n" + jsonString);

                jsonString = jsonb.toJson(negotiationDto);

                LOG.debug("Negotiation - PUT - response\n" + jsonString);

                return Response.status(Response.Status.OK).entity(negotiationDto).build();

        }

}
