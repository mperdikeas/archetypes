package a.b.c.typeA.rest;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;


import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MultivaluedMap;
import javax.mail.internet.ContentDisposition;
    
import javax.xml.datatype.DatatypeConfigurationException;

import javax.sql.DataSource;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringEscapeUtils;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.ST;


public class EmployersServiceBackEnd implements IEmployersServiceBackEnd {

    private IDBDAL db;

    public EmployersServiceBackEnd(DataSource _ds) {
        System.out.printf("%s instance hashcode: [%s]\n"
                          , EmployersServiceBackEnd.class.getName()
                          , System.identityHashCode(this));
        this.db = new DBDAL(_ds);
    }





    @Override
    public String listPersons(String request) {
        List<Person> people = db.listPeople();
        return Printer.print(request, people);
    }
    
}
