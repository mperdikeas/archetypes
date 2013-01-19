import gr.gsis.rgwsbasstoixn.rgwsbasstoixn.*;
import gr.gsis.rgwsbasstoixn.rgwsbasstoixn_wsdl.types.*;
import javax.xml.ws.Holder;
import java.math.BigDecimal;


public class Client {

    public static void main(String args[]) {
        RgWsBasStoixN_Service service = new RgWsBasStoixN_Service();        
        RgWsBasStoixN endpoint = service.getRgWsBasStoixNSoapHttpPort();


        String endPoint = "https://www1.gsis.gr/wsgsis/RgWsBasStoixN/RgWsBasStoixNSoapHttpPort";
        ((javax.xml.ws.BindingProvider)endpoint).getRequestContext().put(
               javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);  

        System.out.println ( "service obtained" );
        Holder<RgWsBasStoixEpitRtUser> out = new Holder<RgWsBasStoixEpitRtUser>();
        RgWsBasStoixEpitRtUser boo         = new RgWsBasStoixEpitRtUser();
        out.value                          = boo;
        Holder<BigDecimal>             seq = new Holder<BigDecimal>();
        seq.value = BigDecimal.ZERO;
        Holder<GenWsErrorRtUser>       err = new Holder<GenWsErrorRtUser>();
        GenWsErrorRtUser foo               = new GenWsErrorRtUser();
        err.value = foo;

        endpoint.rgWsBasStoixEpit("047787907", out, seq, err);

        System.out.println("Zip code is: "+out.value.getPostalZipCode());
        System.out.println("Street   is: "+out.value.getPostalAddress());
        System.out.println("Name     is: "+out.value.getOnomasia());
    }
}