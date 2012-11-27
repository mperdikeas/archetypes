package gmail.mperdikeas.jersey;

import javax.ws.rs.*;

@Path("hellojersey")
public class HelloJersey {

    @GET
    @Produces("text/plain")
    public String getMessage() {
        return "Rest Never Sleeps";
    }

}