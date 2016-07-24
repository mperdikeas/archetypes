package a.b.c.typeA.rest;

import java.util.List;
import java.util.ArrayList;

import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class BaseResource {


    private static List<DataSource> pastDataSources = new ArrayList<>();

    protected DataSource getDataSource() {
        try {
            InitialContext cxt = new InitialContext();
            if ( cxt == null ) {
                throw new RuntimeException("Uh oh -- no context!");
            }
            final String DS_LOOKUP_NAME = "java:/comp/env/jdbc/postgres/silly-test-database";
            DataSource ds = (DataSource) cxt.lookup( DS_LOOKUP_NAME );
            // this shows that the DataSource object is very likely always the same:
            System.out.printf("Identity hashCode of DataSource object is [%s]\n"
                              , System.identityHashCode(ds));
            // this proves that the DataSource object is always the same:
            if (pastDataSources.isEmpty())
                pastDataSources.add(ds);
            else {
                if (!pastDataSources.contains(ds))
                    throw new RuntimeException(String.format("%d datasources exist and none is the newly created one"
                                                             , pastDataSources.size()));
            }
            if ( ds == null ) {
                throw new RuntimeException(String.format("Data source [%s] not found!"
                                                         , DS_LOOKUP_NAME));
            } else
                return ds;
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
