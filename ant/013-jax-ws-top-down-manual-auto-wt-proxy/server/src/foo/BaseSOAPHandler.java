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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import mutil.base.Util;
import mutil.base.ExceptionAdapter;
import mutil.io.IOUtilities;

import _int.esa.esavo.xmlutil.SOAPUtils;
import _int.esa.esavo.xmlutil.XMLUtils;

import org.w3c.dom.Document;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import _int.esa.esavo.common.FileUtil;
import mutil.base.JsonProvider;

public abstract class BaseSOAPHandler extends HttpServlet {

    public static final String RESOURCE_MAP = "RESOURCE_MAP";

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException {
        try {
            String x = getServletConfig().getInitParameter(RESOURCE_MAP);
            System.out.println(x);
            final ResourceMap resourceMap = JsonProvider.fromJsonExt(x, ResourceMap.class);
            System.out.printf("looking for: %s|%s\n", request.getRequestURI(), request.getQueryString());
            ResourceCoord coord = resourceMap.get(request.getRequestURI(), request.getQueryString());
            System.out.println("coord is: "+coord);
            File f = new File(coord.getFilePath());
            System.out.println("file f is: "+f.getPath());
            String content = FileUtil.slurpUTF8(f, coord.getRegExpSubs());
            response.setContentLength( content.length() );
            long bytesCopied = IOUtilities.stream (new ByteArrayInputStream(content.getBytes("UTF-8")), response.getOutputStream());
            if (content.length()!=bytesCopied)
                throw new RuntimeException(String.format("%d|%d", content.length(), bytesCopied));
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }


}
