package a.b.c;

import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.FormParam;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;

import javax.servlet.ServletContext;

import javax.sql.DataSource;


@Path("/somePath")
public class DomainSpecificResource {

    @Context
    ServletContext context;

    public DomainSpecificResource() {
        System.out.printf("%s instance hashcode: [%s]\n"
                          , DomainSpecificResource.class.getName()
                          , System.identityHashCode(this));
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username
                          , @FormParam("password") String password) {
        SessionIdentifierGenerator generator = (SessionIdentifierGenerator) context.getAttribute(Constants.RANDOM_GENERATOR);
        if (username.equals(password)) {
            NewCookie cookie = new NewCookie("session-id", generator.nextSessionId());
            return Response.ok("true").cookie(cookie).build();
        } else
            return Response.ok("false").build();
    }
}
