package gr.neuropublic;

import gr.gsis.rgwsbasstoixn.rgwsbasstoixn.*;
import gr.gsis.rgwsbasstoixn.rgwsbasstoixn_wsdl.types.*;
import javax.xml.ws.Holder;
import java.math.BigDecimal;

import mutil.base.Triad;

public class GsisWSDLClient {


    private RgWsBasStoixN endpoint;
    public GsisWSDLClient(String endpointStr) {
        RgWsBasStoixN_Service service = new RgWsBasStoixN_Service();        
        this.endpoint = service.getRgWsBasStoixNSoapHttpPort();

        ((javax.xml.ws.BindingProvider)this.endpoint).getRequestContext().put(
               javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointStr);
    }

    public Triad<RgWsBasStoixEpitRtUser, BigDecimal, GenWsErrorRtUser> vatData(String vat) {
        Holder<RgWsBasStoixEpitRtUser> out = new Holder<RgWsBasStoixEpitRtUser>();
        RgWsBasStoixEpitRtUser boo         = new RgWsBasStoixEpitRtUser();
        out.value                          = boo;
        Holder<BigDecimal>             seq = new Holder<BigDecimal>();
        seq.value = BigDecimal.ZERO;
        Holder<GenWsErrorRtUser>       err = new Holder<GenWsErrorRtUser>();
        GenWsErrorRtUser foo               = new GenWsErrorRtUser();
        err.value = foo;
        endpoint.rgWsBasStoixEpit(vat, out, seq, err);
        return Triad.create(out.value, seq.value, err.value);
    }

    public String versionInfo() {
        return endpoint.rgWsBasStoixNVersionInfo();
    }

    public static void main(String args[]) {
        GsisWSDLClient client = new GsisWSDLClient("https://www1.gsis.gr/wsgsis/RgWsBasStoixN/RgWsBasStoixNSoapHttpPort");
        System.out.println ( "service obtained" );
        Triad<RgWsBasStoixEpitRtUser, BigDecimal, GenWsErrorRtUser> retValue = client.vatData("047787907");
        System.out.println("Zip code is: "+retValue.a.getPostalZipCode());
        System.out.println("Street   is: "+retValue.a.getPostalAddress());
        System.out.println("Name     is: "+retValue.a.getOnomasia());
    }
}