package com.wsnpbridge;

import gr.gsis.wsnp.WSNPResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

public interface WsNpRESTService {
	
	@GET
	@Path("/{afm}")
	@Produces("application/json; charset=UTF-8")
	public WSNPResponse getDetails(
			@PathParam("afm")String afm
			);
	
	@GET
	@Path("/version")
	public String getVersion();
}
