package foo;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService (endpointInterface = "foo.client.IHello", serviceName="MyService", targetNamespace="urn:playground:jax-ws")
public class Hello implements foo.client.IHello {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + ".";
    }
}