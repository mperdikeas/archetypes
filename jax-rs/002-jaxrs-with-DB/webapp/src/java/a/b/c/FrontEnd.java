package a.b.c;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import a.b.c.IBusinessLogic;
import a.b.c.BusinessLogic;

import d.e.f.IDBDAL;
import d.e.f.DBDAL;


@Path("/application/path")
public class FrontEnd {

    public FrontEnd() {
        System.out.printf("FrontEnd hashcode: [%s]\n"
                          , System.identityHashCode(this));
    }

    private static List<DataSource> pastDataSources = new ArrayList<>();

    private DataSource getDataSource() {
        try {
            InitialContext cxt = new InitialContext();
            if ( cxt == null ) {
                throw new RuntimeException("Uh oh -- no context!");
            }
            final String DS_LOOKUP_NAME = "java:/comp/env/jdbc/postgres/silly-test-database";
            DataSource ds = (DataSource) cxt.lookup( DS_LOOKUP_NAME );
            // this shows that the DataSource object is very likely always the same:
            System.out.printf("Identity hashCode of DataSource object is [%s]\n"
                              , System.identityHashCode(ds));
            // this proves that the DataSource object is always the same:
            if (pastDataSources.isEmpty())
                pastDataSources.add(ds);
            else {
                if (!pastDataSources.contains(ds))
                    throw new RuntimeException(String.format("%d datasources exist and none is the newly created one"
                                                             , pastDataSources.size()));
            }
            if ( ds == null ) {
                throw new RuntimeException(String.format("Data source [%s] not found!"
                                                         , DS_LOOKUP_NAME));
            } else
                return ds;
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }


    @Path("{key}")
    @GET
    @Produces("application/json")
    public Response getValue(@PathParam("key") String key) {
        //TODO: here we need to obtain the datasource
        IBusinessLogic businessLogic = new BusinessLogic();
        IDBDAL dbAPI = new DBDAL(getDataSource());
        return Response.status(200).entity(String.format("sent key was: [%s]"
                                                         , businessLogic.retrieve(
                                                                                  dbAPI
                                                                                  , key))).build();
    }


}
