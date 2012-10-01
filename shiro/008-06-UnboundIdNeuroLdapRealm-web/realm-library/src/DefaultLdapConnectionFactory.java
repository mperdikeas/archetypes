import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultLdapConnectionFactory implements ILdapConnectionFactory { 

    private static final Logger log = LoggerFactory.getLogger(DefaultLdapConnectionFactory.class);

    private String host                                 = null;
    private int    port                                 = 389;
    private String systemUserDN                         = null;
    private String systemPasswd                         = null;

    public void setHost                 (String host           ) { this.host = host ; }
    public void setSystemUserDN         (String systemUserDN   ) { this.systemUserDN = systemUserDN ; }
    public void setSystemPasswd         (String systemPasswd   ) { this.systemPasswd = systemPasswd ; }



    public DefaultLdapConnectionFactory() {
    }

    @Override
    public LDAPConnection getConnection() throws LDAPException { 
        return new LDAPConnection(host, port, systemUserDN, systemPasswd);
    }


    @Override     // used for authentication
    public void canGetThrowAwayLDAPConnection(String userBindDN, String userPasswd) throws LDAPException {
        LDAPConnection throwAwayConnection = null;
        try {
            throwAwayConnection = new LDAPConnection(host, port, userBindDN, userPasswd);
            log.trace("created throw-away connection to LDAP server");
        } finally {
            if (throwAwayConnection != null) throwAwayConnection.close();
            log.trace("throw-away connection to LDAP server is now closed");
        }
    }
}