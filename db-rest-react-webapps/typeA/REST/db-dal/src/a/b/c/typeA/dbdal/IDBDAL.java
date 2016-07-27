package a.b.c.typeA.dbdal;

import java.util.List;
import java.sql.Connection;

public interface IDBDAL {

    Connection      exportConnection        ();
    void            closeExportedConnection (Connection conn);
    List<Person>    listPersons             ();
    void            modifyPerson            (Person     person);
    int             createNewPerson         (PersonBase person);
    boolean         deletePerson            (int i);
}
