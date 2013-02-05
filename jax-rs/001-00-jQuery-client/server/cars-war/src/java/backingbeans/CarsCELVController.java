package backingbeans;

import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;
import java.io.Serializable;

import javax.ws.rs.*;
import javax.ws.rs.core.Response.Status;
import javax.ejb.EJB;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

import mutil.base.JsonProvider;
import mutil.base.Pair;
import mutil.base.Triad;

import facades.*;

@javax.enterprise.context.RequestScoped
@Path("/")
public class CarsCELVController implements Serializable {

    private static final Logger l = LoggerFactory.getLogger(CarsCELVController.class);

    @EJB(beanName = "CarFacade")
    private ICarFacade.ILocal carFacade;

    @GET
    @Produces("application/json; charset=UTF-8")
    @Path("/name")
    public String name() {
        l.info("function called");
        return "name called";
    }

    @GET
    @Produces("application/javascript; charset=UTF-8")
    @Path("/name2")
    public String name2(@QueryParam("callback") String callback) {
        l.info("function name2 called");
        return String.format("%s(\"%s\")", callback, "name called");
    }
}

