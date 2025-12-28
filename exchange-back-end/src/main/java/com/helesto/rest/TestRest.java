package com.helesto.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import com.helesto.dto.OrderDto;
import com.helesto.socket.OrderSocket;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/test")
@Tag(name = "Tests")
@RequestScoped
public class TestRest {

    @Inject
    OrderSocket orderSocket;

    @Path("/order-websocket")
    @POST
    public String getTest(OrderDto orderDto) {

        orderSocket.broadcast(orderDto);

        return "OrderDto broadcasted";

    }

}
