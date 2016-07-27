package a.b.c.typeA.rest;

import a.b.c.typeA.dbdal.IDBDAL;
import a.b.c.typeA.dbdal.Person;
import a.b.c.typeA.dbdal.PersonBase;

public interface IEmployersServiceBackEnd {

    ListPersonsResponse listPersons      (String     request);
    void                modifyPerson     (Person     person);
    int                 createNewPerson  (PersonBase person);
    boolean             deletePerson     (int        idx);
    IDBDAL              getDBAPI();
}
