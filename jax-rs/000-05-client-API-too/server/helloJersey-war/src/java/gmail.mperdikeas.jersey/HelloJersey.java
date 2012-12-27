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

    @GET
    @Produces("text/plain")
    @Path("/")
    public String info(@DefaultValue("Alpha is NULL") @QueryParam("a") String a, @QueryParam("b") String b) {
        return "value of a: "+a+", value of b: "+b;
    }

    @GET
    @Produces("text/html")   // this one returns HTML
    @Path("/browserInfo")
    public String browserInfo(@HeaderParam("User-Agent") String userAgent, @CookieParam("sessionId") String sessionId) {
        return String.format("user agent is: <b>%s</b><p>and cookie 'sessiondId' is: %s", userAgent, sessionId);
    }


}