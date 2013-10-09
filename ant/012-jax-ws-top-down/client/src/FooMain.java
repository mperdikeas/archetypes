import org.apache.commons.lang3.StringUtils;

import playground.jax_ws.IHello;
import playground.jax_ws.MyService;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import javax.xml.ws.BindingProvider;

public class FooMain {

    public static void main(String[] args) throws Exception {
        String serviceURL = "http://localhost:9000/soap?wsdl";
        {   // WAY 1
            URL url = new URL(serviceURL);
            QName qname = new QName("urn:playground:jax-ws", "MyService");
            Service service = Service.create(url, qname);
            IHello port = service.getPort(IHello.class);
            System.out.println(port.sayHello("Long John Silver"));
        }
        {   // WAY 2
            MyService service = new MyService();
            IHello port = service.getHelloPort();
    
            ((javax.xml.ws.BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
            
            System.out.println(port.sayHello("Long John Silver"));
        }
        {   // WAY 3
            URL url = new URL(serviceURL);
            QName qname = new QName("urn:playground:jax-ws", "MyService");
            MyService service = new MyService(url, qname);
            IHello port = service.getHelloPort();
            System.out.println(port.sayHello("Long John Silver"));
        }
        {   // WAY 4
            QName qname = new QName("urn:playground:jax-ws", "MyService");
            // see:
            //    http://stackoverflow.com/questions/8085826/instantiate-jax-ws-service-without-downloading-wsdl
            // - and -
            //    http://stackoverflow.com/questions/18925888/jax-ws-ways-to-call-a-web-service-from-a-standalone-java-7-se-client
            MyService service = new MyService(null, qname);
            IHello port = service.getHelloPort();
            BindingProvider bindingProvider = (BindingProvider) port;
            bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);

            System.out.println(port.sayHello("Long John Silver"));
        }
    }
}
