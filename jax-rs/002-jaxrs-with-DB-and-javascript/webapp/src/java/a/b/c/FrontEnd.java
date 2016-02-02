package a.b.c;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import a.b.c.IBusinessLogic;
import a.b.c.BusinessLogic;

import d.e.f.IDBDAL;
import d.e.f.DBDAL;


@Path("/application/path")
public class FrontEnd {

    @Path("{key}")
    @GET
    @Produces("application/json")
    public Response getValue(@PathParam("key") String key) {
        //TODO: here we need to obtain the datasource
        IBusinessLogic businessLogic = new BusinessLogic();
        IDBDAL dbAPI = new DBDAL();
        return Response.status(200).entity(String.format("sent key was: [%s]"
                                                         , businessLogic.retrieve(
                                                                                  dbAPI
                                                                                  , key))).build();
    }


}
