package a.b.c;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

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
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import javax.servlet.ServletContext;

import javax.sql.DataSource;


@Path("/somePath")
public class LoginResource {

    @Context
    ServletContext context;

    public LoginResource() {
        System.out.printf("%s instance hashcode: [%s]\n"
                          , LoginResource.class.getName()
                          , System.identityHashCode(this));
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username
                          , @FormParam("password") String password) {
        SessionIdentifierGenerator generator = (SessionIdentifierGenerator) context.getAttribute(Constants.RANDOM_GENERATOR);
        if (username.equals(password)) {
            NewCookie cookie = null;
            if (false) {
                cookie = new NewCookie("session-id", generator.nextSessionId());
            } else {
                final String   PATH      = "/"; // make the cookie availble for the entire domain
                final String   DOMAIN    = "127.0.0.1";
                final int      VERSION   = Cookie.DEFAULT_VERSION;
                final String   COMMENT   = null;
                final int      MAX_AGE   = NewCookie.DEFAULT_MAX_AGE; // expires with the current application / browser session
                final boolean  SECURE    = false;
                cookie = new NewCookie("session-id", generator.nextSessionId(), PATH, DOMAIN, VERSION, COMMENT, MAX_AGE, SECURE);
            }
            Response rv = Response.ok("true").cookie(cookie).build();
            MultivaluedMap<String, Object> headers = rv.getMetadata();
            for (String key : headers.keySet())
                System.out.printf("header [%s] -> [%s]\n", key, headers.get(key).toString());
            return rv;
        } else
            return Response.ok("false").build();
    }
}
