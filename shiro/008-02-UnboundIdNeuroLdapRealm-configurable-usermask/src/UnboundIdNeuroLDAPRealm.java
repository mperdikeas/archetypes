import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken; 
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import com.unboundid.ldap.sdk.*;

public class UnboundIdNeuroLDAPRealm extends AuthorizingRealm {



    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

    private static final Logger log = LoggerFactory.getLogger(UnboundIdNeuroLDAPRealm.class);

    /*-------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    protected String principalSuffix                    = null;
    protected String principalRDTag                     = null;
    protected String searchBase                         = null;
    protected String host                               = null;
    protected String systemUserDN                       = null;
    protected String systemPasswd                       = null;
    private LdapConnectionFactory ldapConnectionFactory = null;

    /*-------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /*-------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/
    public void setPrincipalSuffix      (String principalSuffix) { this.principalSuffix = principalSuffix; }
    public void setPrincipalRDTag       (String principalRDTag ) { this.principalRDTag = principalRDTag; }
    public void setSearchBase           (String searchBase     ) { this.searchBase = searchBase; }
    public void setHost                 (String host           ) { this.host = host ; }
    public void setSystemUserDN         (String systemUserDN   ) { this.systemUserDN = systemUserDN ; }
    public void setSystemPasswd         (String systemPasswd   ) { this.systemPasswd = systemPasswd ; }
    public void setLDAPConnectionFactory(LdapConnectionFactory ldapConnectionFactory) { 
                                                                   this.ldapConnectionFactory = ldapConnectionFactory;
    }

    /*--------------------------------------------
    |               M E T H O D S                |
    ============================================*/

    protected void onInit() {
        super.onInit();
        ensureLDAPConnectionFactory();
    }

    private LdapConnectionFactory ensureLDAPConnectionFactory() {
        if (this.ldapConnectionFactory == null) {
            log.debug("No LdapConnectionFactory specified - creating a default instance.");
            LdapConnectionFactory defaultFactory = new LdapConnectionFactory();
            defaultFactory.setHost           (this.host);
            defaultFactory.setSystemUserDN   (this.systemUserDN);
            defaultFactory.setSystemPasswd   (this.systemPasswd);
            this.ldapConnectionFactory = defaultFactory;
        }
        return this.ldapConnectionFactory;
    }


    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        try {
            return queryForAuthenticationInfo(token, ensureLDAPConnectionFactory());

        } catch (LDAPException lde) {
            throw new AuthenticationException("LDAP Authentication over UnboundId failed", lde);
        }
    }

    protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token, LdapConnectionFactory ldapConnectionFactory)
            throws LDAPException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username   = upToken.getUsername();
        String usernameDN = String.format("%s=%s,%s", principalRDTag, username, principalSuffix);
        log.debug(String.format("checking for authentication of '%s'->'%s'", username, usernameDN));
        ldapConnectionFactory.authenticate(usernameDN, String.valueOf(upToken.getPassword()));
        return new SimpleAuthenticationInfo(username, upToken.getPassword(), getName());
    }


    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }/*
        AuthorizationInfo info;
        try {
            info = queryForAuthorizationInfo(principals, ensureContextFactory());
        } catch (NamingException e) {
            String msg = "LDAP naming error while attempting to retrieve authorization for user [" + principals + "].";
            throw new AuthorizationException(msg, e);
        }

        return info;
        }*/




    /*
      protected abstract AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principal, LdapContextFactory ldapContextFactory) throws NamingException;*/

}
