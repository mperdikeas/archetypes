package gmail.mperdikeas.jersey;

import javax.ws.rs.*;

//@Path("sayHi/{username}")   // this works, too (without specifying any regular expression)
@Path("sayHi/{username: [a-zA-Z][a-zA-Z_0-9]*}")
public class HelloJersey {

    @GET
    @Produces("text/plain")
    public String getMessage(@PathParam("username") String userName) {
        return "Hello "+userName+" - Rest Never Sleeps";
    }



}