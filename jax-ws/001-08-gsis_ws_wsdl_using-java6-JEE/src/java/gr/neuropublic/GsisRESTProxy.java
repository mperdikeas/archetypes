package gr.neuropublic;

import java.math.BigDecimal;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

import mutil.base.JsonProvider;
import mutil.base.Triad;

import gr.neuropublic.GsisWSDLClient;

import gr.gsis.rgwsbasstoixn.rgwsbasstoixn.*;
import gr.gsis.rgwsbasstoixn.rgwsbasstoixn_wsdl.types.*;


@Path("/vat")
public class GsisRESTProxy {

    private final static Logger l = LoggerFactory.getLogger(GsisRESTProxy.class);
    GsisWSDLClient client;

    public GsisRESTProxy(){
        client = new GsisWSDLClient("https://www1.gsis.gr/wsgsis/RgWsBasStoixN/RgWsBasStoixNSoapHttpPort");
    }
	

    @GET
    @Path("/{vat}")
    @Produces("application/json; charset=UTF-8")
    public Triad<RgWsBasStoixEpitRtUser, BigDecimal, GenWsErrorRtUser> getDetails(
			@PathParam("vat")String vat
                             ) {
        l.info("received request for service with vat = "+vat);
        Triad<RgWsBasStoixEpitRtUser, BigDecimal, GenWsErrorRtUser> retValue = client.vatData(vat);
        return retValue;
    }

	

}
