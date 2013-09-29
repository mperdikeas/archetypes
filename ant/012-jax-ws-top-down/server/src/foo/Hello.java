package foo;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService (endpointInterface = "playground.jax_ws.IHello", serviceName="MyService", targetNamespace="urn:playground:jax-ws")
public class Hello implements playground.jax_ws.IHello {

    @Override
    public String sayHello(String name) {
        System.out.printf("sayHello('%s') called\n", name);
        return "Hello, " + name + ".";
    }
}