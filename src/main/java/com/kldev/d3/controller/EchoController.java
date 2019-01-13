package com.kldev.d3.controller;


import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("/echo")
public class EchoController {


    @Path("/{text}")
    @Timed
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello(@PathParam("text")String text)
    {
       return "Hello: " + text + " " + new Date().toInstant().toEpochMilli();

    }
}
