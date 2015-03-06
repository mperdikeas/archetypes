import org.apache.commons.lang3.StringUtils;

import playground.jax_ws.IHello;
import playground.jax_ws.MyService;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import javax.xml.ws.BindingProvider;
import _int.esa.esavo.common.CLIUtil;

import foo.bar.zar.MulStringRequest;
import foo.bar.zar.MulStringResponse;

public class FooMain {

    private static MulStringRequest in(String a, int n) {
        MulStringRequest in = new MulStringRequest();
        in.setArg0(a);
        in.setArg1(n);
        return in;
    }

    private static String out(MulStringResponse v) {
        return v.getReturn();
    }

    public static void main(String[] args) throws Exception {
        FooMainCLI cli = CLIUtil.cli(FooMain.class.getName(), args, FooMainCLI.class);
        boolean useProxy = cli.proxy==0?false:true;
        String serviceURLS = String.format("http://localhost:%d/soap?wsdl", useProxy?9001:9000);
        URL serviceURL = new URL(serviceURLS);
        if (!useProxy) {   // WAY 1

            QName qname = new QName("urn:playground:jax-ws", "MyService");
            Service service = Service.create(serviceURL, qname);
            IHello port = service.getPort(IHello.class);
            System.out.println(port.sayHello("Long John Silver"));
            System.out.println(out(port.mulString(in("alpha-", 5))));
        }
        if (!useProxy) {   // WAY 2
            MyService service = new MyService();
            IHello port = service.getHelloPort();
    
            ((javax.xml.ws.BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURLS);
            
            System.out.println(port.sayHello("Long John Silver"));
            System.out.println(out(port.mulString(in("alpha-", 5))));
        }
        if (!useProxy) {   // WAY 3
            QName qname = new QName("urn:playground:jax-ws", "MyService");
            MyService service = new MyService(serviceURL, qname);
            IHello port = service.getHelloPort();
            System.out.println(port.sayHello("Long John Silver"));
            System.out.println(out(port.mulString(in("alpha-", 5))));
        }
        {   // WAY 4
            for (int i = 0 ; i < 5 ; i++) {
                QName qname = new QName("urn:playground:jax-ws", "MyService");
                // see:
                //    http://stackoverflow.com/questions/8085826/instantiate-jax-ws-service-without-downloading-wsdl
                // - and -
                //    http://stackoverflow.com/questions/18925888/jax-ws-ways-to-call-a-web-service-from-a-standalone-java-7-se-client
                MyService service = new MyService(null, qname);
                IHello port = service.getHelloPort();
                BindingProvider bindingProvider = (BindingProvider) port;
                bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURLS);
    
                System.out.println(port.sayHello("Long John Silver"));
                System.out.println(out(port.mulString(in("alpha-", 5))));
            }
        }
    }
}
