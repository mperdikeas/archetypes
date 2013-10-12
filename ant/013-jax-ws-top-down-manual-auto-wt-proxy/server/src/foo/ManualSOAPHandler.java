package foo;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import mutil.base.Util;
import mutil.io.IOUtilities;

import _int.esa.esavo.xmlutil.SOAPUtils;
import _int.esa.esavo.xmlutil.XMLUtils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.parsers.ParserConfigurationException;


import mutil.base.Triad;

import _int.esa.esavo.common.FileUtil;


public class ManualSOAPHandler extends POSTSkeletonSOAPHandler {

    @Override
    public String sayHello(String soapBody) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        System.out.println("inside sayHello implementation method");
        XPath xpath = XMLUtils.namespaceAwareXpath("ns", "http://zar.bar.foo/");
        Document doc = XMLUtils.slurpXML(soapBody);
        String arg0 = (String) xpath.compile("/ns:sayHello/arg0/.").evaluate(doc, XPathConstants.STRING);
        String rv = String.format("hello, %s", arg0);
        String MASK = "<ns2:sayHelloResponse xmlns:ns2=\"http://zar.bar.foo/\"><return>%s</return></ns2:sayHelloResponse>";
        return String.format(MASK, rv);
    }

    @Override
    public String mulString(String soapBody) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        XPath xpath = XMLUtils.namespaceAwareXpath("ns", "http://zar.bar.foo/");
        Document doc = XMLUtils.slurpXML(soapBody);
        String s = (String) xpath.compile("/ns:mulStringRequest/arg0/.").evaluate(doc, XPathConstants.STRING);
        int n    = ( (Double) xpath.compile("/ns:mulStringRequest/arg1/.").evaluate(doc, XPathConstants.NUMBER)).intValue();
        String _rv = null;
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0 ; i < n; i++)
                sb.append(s);
            _rv = sb.toString();
        }
        String MASK = "<ns:mulStringResponse xmlns:ns=\"http://zar.bar.foo/\"><return>%s</return></ns:mulStringResponse>";
        return String.format(MASK, _rv);
    }
}
