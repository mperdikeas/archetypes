package foo;

import javax.xml.ws.Endpoint;

public class FooMain {


    public static void main(String args[]) throws Exception {
        System.out.println("Starting Server");
        Object implementor = new Hello();
        String address = "http://localhost:9000/soap";

        Endpoint.publish(address, implementor);
    }
}
