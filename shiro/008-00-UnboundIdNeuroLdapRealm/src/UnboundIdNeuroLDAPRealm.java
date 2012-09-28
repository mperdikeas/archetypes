import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import com.unboundid.ldap.sdk.*;

public abstract class UnboundIdNeuroLDAPRealm extends AuthorizingRealm {



    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

    private static final Logger log = LoggerFactory.getLogger(UnboundIdNeuroLDAPRealm.class);

    /*-------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    protected String principalSuffix = null;

    protected String searchBase = null;

    protected String url = null;

    protected String systemUsername = null;

    protected String systemPassword = null;

    private Object ldapConnectionFactory = null;

    /*-------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /*-------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

    /*-------------------------------------------
    |               M E T H O D S               |
    ============================================*/


    public void setPrincipalSuffix(String principalSuffix) {
        this.principalSuffix = principalSuffix;
    }

    public void setSearchBase(String searchBase) {
        this.searchBase = searchBase;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSystemUsername(String systemUsername) {
        this.systemUsername = systemUsername;
    }


    public void setSystemPassword(String systemPassword) {
        this.systemPassword = systemPassword;
    }


    public void setLDAPConnectionFactory(Object ldapConnectionFactory) {
        this.ldapConnectionFactory = ldapConnectionFactory;
    }

    /*--------------------------------------------
    |               M E T H O D S                |
    ============================================*/

    protected void onInit() {
        super.onInit();
        ensureLDAPConnectionFactory();
    }

    private Object ensureLDAPConnectionFactory() {
        if (this.ldapConnectionFactory == null) {
            log.debug("No LdapConnectionFactory specified - creating a default instance.");

            /*
            DefaultLdapContextFactory defaultFactory = new DefaultLdapContextFactory();
            defaultFactory.setPrincipalSuffix(this.principalSuffix);
            defaultFactory.setSearchBase(this.searchBase);
            defaultFactory.setUrl(this.url);
            defaultFactory.setSystemUsername(this.systemUsername);
            defaultFactory.setSystemPassword(this.systemPassword);

            this.ldapConnectionFactory = defaultFactory;
            */
        }
        return this.ldapConnectionFactory;
    }


    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AuthenticationInfo info;
        try {
            info = queryForAuthenticationInfo(token, ensureLDAPConnectionFactory());
        } catch (javax.naming.AuthenticationException e) {
            throw new AuthenticationException("LDAP authentication failed.", e);
        } catch (NamingException e) {
            String msg = "LDAP naming error while attempting to authenticate user.";
            throw new AuthenticationException(msg, e);
        }

        return info;
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


    protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token, Object ldapConnectionFactory) throws NamingException {
        return null;
    }


    /*
      protected abstract AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principal, LdapContextFactory ldapContextFactory) throws NamingException;*/

}
