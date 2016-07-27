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
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.mail.internet.ContentDisposition;
    
import javax.sql.DataSource;

import javax.xml.datatype.DatatypeConfigurationException;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringEscapeUtils;

import mutil.base.TimeUtils;

import a.b.c.typeA.dbdal.Person;
import a.b.c.typeA.dbdal.PersonBase;

import a.b.c.typeA.rest.printer.Printer;

import com.google.common.base.Throwables;


@Path("/es") 
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
    @Produces(MediaType.TEXT_PLAIN)
    public Response listPersons() {
        System.out.printf("%s#%s()\n", EmployersService.class.getName(), "listPersons");
        try {
            ListPersonsResponse response =  backEnd.listPersons(uriInfo.getAbsolutePath().toURL().toString());
            return Response.status(Response.Status.OK).entity(Printer.print(response)).build();
        } catch (Exception e) {
            throw new WebApplicationException(e);
        }
    }

    /* the second method showcases relying on implicit JSON-ification (in the first method we supply
        our own JSON printer)
     */        
    @GET
    @Path("/listPersons2")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPersons2() {
        System.out.printf("%s#%s()\n", EmployersService.class.getName(), "listPersons2");
        try {
            ListPersonsResponse rv = backEnd.listPersons(uriInfo.getAbsolutePath().toURL().toString());
            return Response.status(Response.Status.OK).entity(rv).build();
        } catch (Exception e) {
            throw new WebApplicationException(e);
        }
    }

    @PUT
    @Path("/modifyPerson")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifyPerson(Person newPerson) {
        try {
            backEnd.modifyPerson(newPerson);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            throw new WebApplicationException(e);
        }
    }

    /* In the below method I am using a different mechanism to communicate the exceptional
       situation to the front end. I think this mechanism allows more information to be 
       conveyed.
     */

    @POST
    @Path("/createNewPerson")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response createNewPerson(PersonBase newPerson) {
        try {
            int i = backEnd.createNewPerson(newPerson);
            return Response.status(Response.Status.OK).entity(i).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Throwables.getStackTraceAsString(e)).build();
        }
    }

    @DELETE
    @Path("/deletePerson/{idx}")
    public Response deletePerson(@PathParam("idx") int idx) {
        try {
            boolean successfulDeletion = backEnd.deletePerson(idx);
            if (successfulDeletion)
                return Response.status(Response.Status.OK).build();
            else
                return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Throwables.getStackTraceAsString(e)).build();
        }
    }        
}
