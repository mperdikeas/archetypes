import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ILdapConnectionFactory {

    public LDAPConnection getConnection() throws LDAPException ;

    public void canGetThrowAwayLDAPConnection(String userBindDN, String userPasswd) throws LDAPException ;

    public void setHost                 (String host           ) ;
    public void setSystemUserDN         (String systemUserDN   ) ;
    public void setSystemPasswd         (String systemPasswd   ) ;


}