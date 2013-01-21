package gr.neuropublic;

import java.math.BigDecimal;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

import mutil.base.JsonProvider;
import mutil.base.Triad;

import gr.neuropublic.GsisWSDLClient;

import gr.gsis.rgwsbasstoixn.rgwsbasstoixn.*;
import gr.gsis.rgwsbasstoixn.rgwsbasstoixn_wsdl.types.*;


@Path("/gsis")
public class GsisRESTProxy {

    private final static Logger l = LoggerFactory.getLogger(GsisRESTProxy.class);

    private static void assertMandatoryParams(Object ...params) {
        for (Object param : params) {
            l.info(String.format("checkning param: %s", param));
            if (param == null) throw new WebApplicationException(javax.ws.rs.core.Response.Status.BAD_REQUEST);
        }
    }

    GsisWSDLClient client;

    public GsisRESTProxy(){
        client = new GsisWSDLClient("https://www1.gsis.gr/wsgsis/RgWsBasStoixN/RgWsBasStoixNSoapHttpPort");
    }
	

    @GET
    @Path("/find")
    @Produces("application/json; charset=UTF-8")
    public Triad<RgWsBasStoixEpitRtUser, BigDecimal, GenWsErrorRtUser> getDetails(
			@QueryParam("vat")String vat
                             ) {
        assertMandatoryParams(vat);
        l.info("received request for service with vat = "+vat);
        Triad<RgWsBasStoixEpitRtUser, BigDecimal, GenWsErrorRtUser> retValue = client.vatData(vat);
        return retValue;
    }

    @GET
    @Path("/version")
    @Produces("application/json; charset=UTF-8")
    public String versionInfo() {
        return client.versionInfo();
    }

	

}
