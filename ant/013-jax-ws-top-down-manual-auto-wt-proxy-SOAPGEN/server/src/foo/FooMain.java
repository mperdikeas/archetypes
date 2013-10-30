package foo;

// WSDL imports
import javax.xml.ws.Endpoint;

// non-WSDL imports
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

import _int.esa.esavo.common.CLIUtil;

import mutil.base.JsonProvider;

import _int.esa.esavo.webservices.soap.direct.BaseSOAPHandler;
import _int.esa.esavo.webservices.soap.direct.ResourceMap;
import _int.esa.esavo.webservices.soap.direct.ResourceMapBuilder;

public class FooMain {


    public static void main(String args[]) throws Exception {
        FooMainCLI cli = CLIUtil.cli(FooMain.class.getName(), args, FooMainCLI.class);
        boolean wsimport = cli.wsimport==0?false:true;
        final String ADDRESS = "http://localhost:9000/soap";
        {
            String foo = createResourceMapString(ADDRESS);
            System.out.println(foo);
            ResourceMap map = JsonProvider.fromJsonExt(foo, ResourceMap.class);
        }



        if (wsimport) {
            System.out.println("using the WSDL-generated stubs");
            System.out.println("Starting Server");
            Object implementor = new Hello();
            Endpoint.publish(ADDRESS, implementor);
        } else {
            ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            {
                contextHandler.setContextPath("/");
                ServletHolder manualSOAP = new ServletHolder(foo.ManualSOAPHandler.class);
                manualSOAP.setInitParameter(BaseSOAPHandler.RESOURCE_MAP, createResourceMapString(ADDRESS));
                contextHandler.addServlet(manualSOAP, "/soap/*");
            }
            Server server = new Server(9000);
            server.setHandler(contextHandler);
            server.start();
            server.join();
        }
    }

    private static String createResourceMapString(String ADDRESS) {
        ResourceMap map = 
            (new ResourceMapBuilder()).addConfig("/soap"            , "wsdl", "../wsdl/specs/MyService.wsdl", "REPLACE_WITH_ACTUAL_URL", false, ADDRESS)
                                      .addConfig("/soap/IHello.wsdl", null  , "../wsdl/specs/soap/IHello.wsdl")
                                      .spit();
        return JsonProvider.toJsonExt(map, ResourceMap.class);
    }
}
