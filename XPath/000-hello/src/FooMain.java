import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathConstants;

public class FooMain {

    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        System.out.println("standard JAXP (Java API for XML Processing) and DOM stuff - nothing new");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("books.xml");
        String xmlVersion = doc.getXmlVersion();
        System.out.println("xml version: "+xmlVersion);

        System.out.println("we now proceed to xpath stuff:");
        {
            XPathFactory xfactory = XPathFactory.newInstance();
            XPath xpath = xfactory.newXPath();
            XPathExpression expr = xpath.compile("//book[author='Neal Stephenson']/title/text()");
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            for (int i = 0 ; i < nodes.getLength(); i++)
                System.out.println(nodes.item(i).getNodeValue());
        }
    }

}