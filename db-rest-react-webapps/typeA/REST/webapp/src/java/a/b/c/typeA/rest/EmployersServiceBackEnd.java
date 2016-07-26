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
import javax.sql.DataSource;

import javax.xml.datatype.DatatypeConfigurationException;

import javax.ws.rs.WebApplicationException;


import com.google.common.base.Joiner;

import a.b.c.typeA.dbdal.IDBDAL;
import a.b.c.typeA.dbdal.DBDAL;
import a.b.c.typeA.dbdal.Person;



import mutil.base.TimeUtils;

public class EmployersServiceBackEnd implements IEmployersServiceBackEnd {

    private IDBDAL db;

    public EmployersServiceBackEnd(DataSource _ds) {
        System.out.printf("%s instance hashcode: [%s]\n"
                          , EmployersServiceBackEnd.class.getName()
                          , System.identityHashCode(this));
        this.db = new DBDAL(_ds);
    }





    @Override
    public ListPersonsResponse listPersons(String request) {
        List<Person> persons = db.listPersons();
        try {
            return new ListPersonsResponse(request
                                           , TimeUtils.nowXMLGregorianZRnd().toString()
                                           , persons);
        } catch (DatatypeConfigurationException e) {
            throw new WebApplicationException(e);
        }
    }

    @Override
    public void modifyPerson(Person person) {
        db.modifyPerson(person);
    }

    @Override
    public IDBDAL getDBAPI() {
        return db;
    }
}
