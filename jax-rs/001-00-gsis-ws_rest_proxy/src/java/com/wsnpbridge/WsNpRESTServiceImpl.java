package com.wsnpbridge;

import gr.gsis.wsnp.WSNPProxy;
import gr.gsis.wsnp.WSNPResponse;
import java.util.logging.Logger;
import javax.ws.rs.Path;

@Path("/afm")
public class WsNpRESTServiceImpl implements WsNpRESTService{
	private final static Logger logger = Logger.getLogger(WsNpRESTServiceImpl.class.getName());
	WSNPProxy proxy;
	
	public WsNpRESTServiceImpl(){
		proxy = new WSNPProxy("https://www1.gsis.gr/wsgsis/RgWsBasStoixN/RgWsBasStoixNSoapHttpPort");
	}
	
	public WSNPResponse getDetails(String afm){
		WSNPResponse response  = proxy.rgWsBasStoixN(afm);
		logger.info("AFM: " + afm + " - Onomasia: " + response.pBasStoixNRecOut.getOnomasia());		
		return response;
	}
	
	public String getVersion() {
		String version = proxy.rgWsBasStoixNVersionInfo();
		logger.info("Service Version: " + version);
		return version;
	}
}
