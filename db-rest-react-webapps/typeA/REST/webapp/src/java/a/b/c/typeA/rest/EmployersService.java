package a.b.c.typeA.rest;

import java.util.List;
import java.util.ArrayList;
import java.net.URI;
import java.net.HttpURLConnection;
    
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MultivaluedMap;
import javax.mail.internet.ContentDisposition;
    
import javax.sql.DataSource;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringEscapeUtils;


/*
    Some typical URL I use for testing:

        http://localhost:8080/oaipmh/listPersons

 */

@Path("/oaipmh") 
public class EmployersService extends BaseResource {

    private  IEmployersServiceBackEnd backEnd;
    public EmployersService() {
        System.out.printf("%s instance hashcode: [%s]\n"
                          , EmployersService.class.getName()
                          , System.identityHashCode(this));
        this.backEnd = new EmployersServiceBackEnd(getDataSource());
    }

    @Context
    private UriInfo uriInfo;


    @GET
    @Path("/listPersons")
    @Produces(MediaType.APPLICATION_JSON)
    public String listPersons() {
        System.out.printf("%s#%s()\n", EmployersService.class.getName(), "listPersons");
        return backEnd.listPersons(uriInfo.getRequestUri().getPath());
    }
 

    private String baseURL() {
        URI uri = uriInfo.getAbsolutePath(); // getBaseUri(); // uriInfo.getRequestUri().getPath();
        String request = String.format("http://%s:%s%s", uri.getHost(), uri.getPort(), uri.getPath());
        String xmlEscapedRequest = StringEscapeUtils.escapeXml(request);
        return xmlEscapedRequest;
    }

    
}
