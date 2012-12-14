package gmail.mperdikeas.jersey;

import javax.ws.rs.*;

@Path("/actions")
public class HelloJersey {

    @GET
    @Produces("text/plain")
    @Path("/say/{greetings}/to/{username: [a-zA-Z][a-zA-Z_0-9]*}")
    public String sayGreetings(@PathParam("greetings") String greetings, @PathParam("username") String userName) {
        return "say "+greetings+" to "+userName+" - Rest Never Sleeps";
    }

    @GET
    @Produces("text/plain")
    @Path("/do/{thingToDo}/to/{username: [a-zA-Z][a-zA-Z_0-9]*}")
    public String doAction(@PathParam("thingToDo") String thingToDo, @PathParam("username") String userName) {
        return "do this: '"+thingToDo+"' to "+userName+" - Rest Never Sleeps";
    }

}