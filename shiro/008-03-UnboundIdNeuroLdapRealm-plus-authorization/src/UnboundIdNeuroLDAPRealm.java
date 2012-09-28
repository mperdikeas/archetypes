import java.util.Set;
import java.util.LinkedHashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authc.UsernamePasswordToken; 
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;



import javax.naming.NamingException;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.Filter;


public class UnboundIdNeuroLDAPRealm extends AuthorizingRealm {



    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

    private static final Logger log = LoggerFactory.getLogger(UnboundIdNeuroLDAPRealm.class);

    /*-------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    private String principalSuffix                      = null;
    private String principalRDTag                       = null;
    private String searchBase                           = null;
    private String host                                 = null;
    private String systemUserDN                         = null;
    private String systemPasswd                         = null;
    private ILdapConnectionFactory ldapConnectionFactory= null;

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
    public void setLDAPConnectionFactory(ILdapConnectionFactory ldapConnectionFactory) { 
                                                                   this.ldapConnectionFactory = ldapConnectionFactory;
    }

    /*--------------------------------------------
    |               M E T H O D S                |
    ============================================*/

    protected void onInit() {
        super.onInit();
        ensureLDAPConnectionFactory();
    }

    private ILdapConnectionFactory ensureLDAPConnectionFactory() {
        if (this.ldapConnectionFactory == null) {
            log.debug("No LdapConnectionFactory specified - creating a default instance.");
            ILdapConnectionFactory defaultFactory = new DefaultLdapConnectionFactory();
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

    protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token, ILdapConnectionFactory ldapConnectionFactory)
            throws LDAPException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username   = upToken.getUsername();
        String usernameDN = dnFromUsername(username);
        log.debug(String.format("checking for authentication of '%s'->'%s'", username, usernameDN));
        ldapConnectionFactory.canGetThrowAwayLDAPConnection(usernameDN, String.valueOf(upToken.getPassword()));
        return new SimpleAuthenticationInfo(username, upToken.getPassword(), getName());
    }

    private String dnFromUsername(String username) {
        String usernameDN = String.format("%s=%s,%s", principalRDTag, username, principalSuffix);
        log.debug(String.format("translated: '%s' to: '%s'", username, usernameDN));
        return usernameDN;
    }


    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AuthorizationInfo info;
        try {
            info = queryForAuthorizationInfo(principals, ensureLDAPConnectionFactory());
        } catch (LDAPException e) {
            String msg = "LDAP Exception: Authorization over UnboundId failed while attempting to retrieve authorization for user [" + principals + "].";
            throw new AuthorizationException(msg, e);
        }
        return info;
    }


    protected AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principals, ILdapConnectionFactory ldapConnectionFactory)
        throws LDAPException, LDAPSearchException {
        String username = (String) getAvailablePrincipal(principals);
        String usernameDN = dnFromUsername(username);
        LDAPConnection ldapConn = ldapConnectionFactory.getConnection();
        Set<String> roleNames;
        try {
            roleNames = getRoleNamesForUser(ldapConn, usernameDN);
        } finally {
            ldapConn.close();
        }
        return new SimpleAuthorizationInfo(roleNames);
    }


    private Set<String> getRoleNamesForUser(LDAPConnection ldapConn, String usernameDN) throws LDAPException {
        // 1st implementation: we don't translate the groups into roles in any manner
        // or do any sort of clever traversal routine
        /*Set<String> roleNames;
          roleNames = new LinkedHashSet<String>();*/
        return getGroupsWithUser(ldapConn, usernameDN);
    }


    private Set<String> getGroupsWithUser(LDAPConnection conn, String userDN) throws LDAPSearchException {
        Filter both               = Filter.createANDFilter(
                                        Filter.createEqualityFilter("objectclass", "orclGroup"),
                                        Filter.createEqualityFilter("uniqueMember", userDN)
                                    );
        SearchRequest sr = new SearchRequest(searchBase, SearchScope.SUB, both, "cn");

        SearchResult searchResult = conn.search(sr);
        log.debug(searchResult.getEntryCount()+" matching entries returned");
        Set<String> retValue = new LinkedHashSet<String>();
        for (SearchResultEntry entry : searchResult.getSearchEntries())
            retValue.add(entry.getDN());
        return retValue;
    }



}
