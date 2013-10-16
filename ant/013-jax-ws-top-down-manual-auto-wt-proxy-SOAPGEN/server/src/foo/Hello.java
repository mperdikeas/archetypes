package foo;

import javax.jws.WebService;
import javax.jws.WebMethod;

import foo.bar.zar.MulStringRequest;
import foo.bar.zar.MulStringResponse;

@WebService (endpointInterface = "playground.jax_ws.IHello", serviceName="MyService", targetNamespace="urn:playground:jax-ws")
public class Hello implements playground.jax_ws.IHello {

    @Override
    public String sayHello(String name) {
        System.out.printf("sayHello('%s') called\n", name);
        return "Hello, " + name + ".";
    }

    @Override
    public MulStringResponse mulString(MulStringRequest parameters) {
        MulStringResponse rv = new MulStringResponse();
        String _rv = null;
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0 ; i < parameters.getArg1(); i++)
                sb.append(parameters.getArg0());
            _rv = sb.toString();
        }
        rv.setReturn(_rv);
        return rv;
    }


}