package gr.gsis.wsnp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "RgWsBasStoixN", targetNamespace = "http://gr/gsis/rgwsbasstoixn/RgWsBasStoixN.wsdl", wsdlLocation = "https://www1.gsis.gr/wsgsis/RgWsBasStoixN/RgWsBasStoixNSoapHttpPort?wsdl")
public class RgWsBasStoixN_Service
    extends Service
{

    private final static URL RGWSBASSTOIXN_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(gr.gsis.wsnp.RgWsBasStoixN_Service.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = gr.gsis.wsnp.RgWsBasStoixN_Service.class.getResource(".");
            url = new URL(baseUrl, "https://www1.gsis.gr/wsgsis/RgWsBasStoixN/RgWsBasStoixNSoapHttpPort?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'https://www1.gsis.gr/wsgsis/RgWsBasStoixN/RgWsBasStoixNSoapHttpPort?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        RGWSBASSTOIXN_WSDL_LOCATION = url;
    }

    public RgWsBasStoixN_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RgWsBasStoixN_Service() {
        super(RGWSBASSTOIXN_WSDL_LOCATION, new QName("http://gr/gsis/rgwsbasstoixn/RgWsBasStoixN.wsdl", "RgWsBasStoixN"));
    }

    @WebEndpoint(name = "RgWsBasStoixNSoapHttpPort")
    public RgWsBasStoixN getRgWsBasStoixNSoapHttpPort() {
        return super.getPort(new QName("http://gr/gsis/rgwsbasstoixn/RgWsBasStoixN.wsdl", "RgWsBasStoixNSoapHttpPort"), RgWsBasStoixN.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RgWsBasStoixN
     */
    @WebEndpoint(name = "RgWsBasStoixNSoapHttpPort")
    public RgWsBasStoixN getRgWsBasStoixNSoapHttpPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://gr/gsis/rgwsbasstoixn/RgWsBasStoixN.wsdl", "RgWsBasStoixNSoapHttpPort"), RgWsBasStoixN.class, features);
    }

}
