import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.StringReader;
import java.io.PrintWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList; 
import java.util.Collections; 
import java.util.Map; 
import java.util.HashMap;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext; 
import javax.xml.bind.JAXBException; 
import javax.xml.bind.Marshaller; 
import javax.xml.bind.JAXBElement; 
import javax.xml.bind.Unmarshaller;

import org.xml.sax.SAXException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Validator;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.ls.LSResourceResolver;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;


// autogen packages:
import foo.a.b.c.*;

import org.w3c.dom.Node;

public class FooMain {

    public static void main(String args[]) throws Exception {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        JAXBContext payloadContext = JAXBContext.newInstance("foo.a.b.c");
        Unmarshaller unmarshaller = payloadContext.createUnmarshaller();
        unmarshaller.setSchema(schemaFactory.newSchema(new Source[]{new StreamSource(new FileInputStream(new File("A.xsd")))}));
        JAXBElement<?> oUnmarshalled = (JAXBElement<?>) unmarshaller.unmarshal(new File("a.xml"));
        Object o = oUnmarshalled.getValue();
        System.out.println("the type of object is: "+o.getClass().getName());
        Type type = ((Root) o).getType();
        Foo foo = ( (Root) o).getFoo();
        System.out.println("type is: "+type);
        System.out.println("foo is: "+foo);
        if (type != null)
            System.out.println("value is: "+type.value());
        System.out.printf("%s: %s, cry is: %s\n", foo.getValueAttribute(), foo.getName(), foo.getValue());

    }
}