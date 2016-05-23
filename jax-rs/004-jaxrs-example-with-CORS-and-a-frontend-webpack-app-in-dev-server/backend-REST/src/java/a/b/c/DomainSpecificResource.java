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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
    

import javax.sql.DataSource;



@Path("/somePath")
public class DomainSpecificResource extends BaseResource {

    public DomainSpecificResource() {
        System.out.printf("%s instance hashcode: [%s]\n"
                          , DomainSpecificResource.class.getName()
                          , System.identityHashCode(this));
    }

    @Path("/foo")
    @GET
    @Produces("text/plain")
    public String foo() {
        return "{\"Foo\": 1, \"bar\":2, \"jar\": 3}";
    }

}
