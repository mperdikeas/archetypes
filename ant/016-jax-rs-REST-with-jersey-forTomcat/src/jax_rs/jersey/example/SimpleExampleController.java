package jax_rs.jersey.example;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;



@Path("/webservice")
public class SimpleExampleController {

    private static final PersonInfo PERSON_INFO = new PersonInfo("Winston", "Churchill", 1887);

     @GET  
     @Path("/hello")  
     @Produces("text/plain")  
     public String hello(){  
         return "Hello World!";
     }

    @GET  
    @Path("/echo/{message}")  
    @Produces("text/plain")  
    public String echo(@PathParam("message") String message) {
        return message;
    }

    @GET
    @Path("/echo2")
    @Produces("text/plain")
    public String echo2(@QueryParam("verb") String verb, @QueryParam("action") String action) {
        return String.format("I %s %s", verb, action);
    }

    @GET
    @Path("/person")
    @Produces("application/xml")
    public PersonInfo person() {
        return PERSON_INFO;
    }

    @GET
    @Path("/person2")
    @Produces("text/xml")
    public PersonInfo person2() {
        return PERSON_INFO;
    }

    @POST
    @Consumes({"application/x-www-form-urlencoded"})
    @Path("/getRecord")
    @Produces("text/xml")
    public String getRecord(@FormParam("verb") String verb, @FormParam("identifier") String identifier, @FormParam("metadataPrefix") String metadataPrefix) {
        String rv = String.format("%-20s is: %s\n%-20s is: %s\n%-20s is: %s\n", "verb", verb, "identifier", identifier, "metadataPrefix", metadataPrefix);
        return rv;
    }


}