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


public abstract class POSTSkeletonSOAPHandler extends BaseSOAPHandler {

    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException {
        try {
            System.out.printf("requestURI= '%s'\n", request.getRequestURI());
            System.out.printf("query String= '%s'\n", request.getQueryString());
            if (request.getMethod().equals("POST") && ((request.getQueryString()==null) || (request.getQueryString().equals("wsdl")))) {
                String requestData = IOUtilities.stringifyUTF8(request.getInputStream());
                System.out.printf("request data is:\n%s\n", requestData);
                String soapBody = null;
                try {
                    soapBody = SOAPUtils.soapBody(requestData);
                } catch (Exception e) {
                    throw new ServletException(e);
                }
                System.out.printf("SOAP body is: '%s'\n", soapBody);
                Triad<String, String, String> name_pref_uri = XMLUtils.getLocalnamePrefixNamespaceOfRootElement((String)soapBody);
                String soapBodyPayload = null;
                if (name_pref_uri.c.equals("http://zar.bar.foo/") && name_pref_uri.a.equals("sayHello"))
                    soapBodyPayload = sayHello(soapBody);
                else if (name_pref_uri.c.equals("http://zar.bar.foo/") && name_pref_uri.a.equals("mulStringRequest"))
                    soapBodyPayload = mulString(soapBody);
                else
                    throw new RuntimeException(String.format("%s|%s", name_pref_uri.c, name_pref_uri.a));
                String soapEnvelope = SOAPUtils.putInSoapBody(soapBodyPayload);
                response.setContentType("text/xml; charset=utf-8");
                response.setContentLength(soapEnvelope.length());
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(soapEnvelope);
            } else throw new RuntimeException();
        } catch (XPathExpressionException | ParserConfigurationException | SAXException | IOException e) {
            throw new ServletException(e);
        }
    }

    abstract String sayHello (String soapBody) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException;
    abstract String mulString(String soapBody) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException;

}
