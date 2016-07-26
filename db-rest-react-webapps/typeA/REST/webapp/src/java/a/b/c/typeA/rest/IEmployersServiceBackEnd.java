package a.b.c.typeA.rest;

import a.b.c.typeA.dbdal.IDBDAL;
import a.b.c.typeA.dbdal.Person;

public interface IEmployersServiceBackEnd {

    ListPersonsResponse listPersons (String request);
    void modifyPerson (Person person);
    IDBDAL getDBAPI();
}
