package shirorealm;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.shiro.realm.jdbc.JdbcRealm;

public class HardcodedJdbcRealm extends JdbcRealm {

    // we're using a salted realm so we're no longer using the default query
    /*    protected static final String DEFAULT_AUTHENTICATION_QUERY =
          "SELECT a.\"USER_PASSWORD\" FROM \"SS_USERS\" a WHERE a.\"USER_NAME\" = ?";  */

    protected static final String OVERRIDEN_SALTED_AUTHENTICATION_QUERY =
    "SELECT a.\"USER_PASSWORD\", a.\"USER_PASSWORD_SALT\" FROM \"SS_USERS\" a WHERE a.\"USER_NAME\" = ?"; 

    protected static final String OVERRIDEN_PERMISSIONS_QUERY =
    "SELECT c.\"SYEL_DESCRIPTION\" FROM \"SS_ROLES\"              a                                    INNER JOIN "+
    "                                   \"SS_ROLE_ACCESS_RIGHTS\" b ON (a.\"ROLE_ID\" = b.\"ROLE_ID\") INNER JOIN "+
    "                                   \"SS_SYSTEM_ELEMENTS\"    c ON (b.\"SYEL_ID\" = c.\"SYEL_ID\")            "+
    " WHERE a.\"ROLE_CAPTION\" = ?                                                                                ";
    
    protected static final String OVERRIDEN_USER_ROLES_QUERY = 
    "SELECT a.\"ROLE_CAPTION\" FROM \"SS_ROLES\"      a                                    INNER JOIN "+
    "                               \"SS_USER_ROLES\" b ON (a.\"ROLE_ID\" = b.\"ROLE_ID\") INNER JOIN "+
    "                               \"SS_USERS\"      c ON (b.\"USER_ID\" = c.\"USER_ID\")            "+
    "WHERE c.\"USER_NAME\" = ?                                                                        ";


    public HardcodedJdbcRealm() {
        super();
        InitialContext ic;
        DataSource dataSource;
        try {
            System.out.println("1");
            ic = new InitialContext();
            System.out.println("2");
            dataSource = (DataSource) ic.lookup("jdbc/auth");
            System.out.println("3");
            System.out.println("data source is: "+dataSource);
            this.setDataSource(dataSource);
            System.out.println("4");
            this.setSaltStyle(JdbcRealm.SaltStyle.COLUMN);
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException(); // if we can't find the source, we should panic
        }
        setAuthenticationQuery(OVERRIDEN_SALTED_AUTHENTICATION_QUERY);
        setUserRolesQuery     (OVERRIDEN_USER_ROLES_QUERY           );
        setPermissionsQuery   (OVERRIDEN_PERMISSIONS_QUERY          );
    }
}

