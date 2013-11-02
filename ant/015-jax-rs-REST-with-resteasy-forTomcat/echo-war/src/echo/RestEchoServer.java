package echo;

import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;
import java.io.Serializable;

import javax.ws.rs.*;
import javax.ws.rs.core.Response.Status;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

import mutil.base.JsonProvider;
import mutil.base.Pair;
import mutil.base.Triad;

@Path("/")
public class RestEchoServer implements Serializable {

    private static final Logger l = LoggerFactory.getLogger(RestEchoServer.class);

    @GET
    @Produces("application/json; charset=UTF-8")
    @Path("/echo")
    public String echo(@QueryParam("msg") String msg) {
        return String.format("message was: '%s'", msg);
    }
}

