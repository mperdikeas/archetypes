import com.unboundid.ldap.sdk.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LdapConnectionFactory { // the name of the class will have to be changed to DefaultLdapConnectionFactory
                                     // and provide setters and getters for the fields, and also declare the interface class

    private static final Logger log = LoggerFactory.getLogger(LdapConnectionFactory.class);

    private static final String host   = "172.31.128.30";
    private static final int    port   = 389;
    private static final String bindDN = "cn=orcladmin,cn=Users,dc=neuropublic,dc=gr";
    private static final String passwd = "welcome1";


    public LdapConnectionFactory() {
    }

    public LDAPConnection getConnection() throws LDAPException { 
        return new LDAPConnection(host, port, bindDN, passwd);
    }

    public void authenticate(String userBindDN, String userPasswd) throws LDAPException {
        LDAPConnection garbageConn = null;
        try {
            garbageConn = new LDAPConnection(host, port, userBindDN, userPasswd);
            log.trace("created silly connection to LDAP server");
        } finally {
            garbageConn.close();
            log.trace("silly connection to LDAP server (used only for authentication) is now closed");
        }
    }
}