package a.b.c.typeA.rest;

import a.b.c.typeA.dbdal.IDBDAL;

public interface IEmployersServiceBackEnd {

    ListPersonsResponse listPersons (String request);
    IDBDAL getDBAPI();
}
