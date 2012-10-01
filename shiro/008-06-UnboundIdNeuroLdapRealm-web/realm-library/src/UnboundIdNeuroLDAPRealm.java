import java.util.Set;
import java.util.LinkedHashSet;
import java.util.List;

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
import com.unboundid.ldap.sdk.Attribute;

import mutil.base.Pair;

public class UnboundIdNeuroLDAPRealm extends AuthorizingRealm {

    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

    private static final Logger log = LoggerFactory.getLogger(UnboundIdNeuroLDAPRealm.class);

    /*-------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    private String  principalSuffix                      = null;
    private String  principalRDTag                       = null;
    private String  searchBase                           = null;
    private String  host                                 = null;
    private String  systemUserDN                         = null;
    private String  systemPasswd                         = null;
    private boolean reportRolesAndPrivilligesWithRDOnly  = false;
    private String rolesAndPrivilligesSuffix            = null;
    private String rolesAndPrivilligesRDTag             = null;
    private ILdapConnectionFactory ldapConnectionFactory= null;

    /*-------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /*-------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/
    public void setPrincipalSuffix                     (String  principalSuffix) { this.principalSuffix = principalSuffix ; }
    public void setPrincipalRDTag                      (String  principalRDTag ) { this.principalRDTag = principalRDTag   ; }
    public void setSearchBase                          (String  searchBase     ) { this.searchBase = searchBase           ; }
    public void setHost                                (String  host           ) { this.host = host                       ; }
    public void setSystemUserDN                        (String  systemUserDN   ) { this.systemUserDN = systemUserDN       ; }
    public void setSystemPasswd                        (String  systemPasswd   ) { this.systemPasswd = systemPasswd       ; }
    public void setReportRolesAndPrivilligesWithRDOnly (boolean boolValue      ) { this.reportRolesAndPrivilligesWithRDOnly = boolValue ; }
    public void setRolesAndPrivilligesSuffix           (String  strValue       ) { this.rolesAndPrivilligesSuffix           = strValue  ; }
    public void setRolesAndPrivilligesRDTag            (String  strValue       ) { this.rolesAndPrivilligesRDTag             = strValue  ; }
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
        log.info(String.format("checking for authentication of '%s'->'%s'", username, usernameDN));
        ldapConnectionFactory.canGetThrowAwayLDAPConnection(usernameDN, String.valueOf(upToken.getPassword()));
        return new SimpleAuthenticationInfo(username, upToken.getPassword(), getName());
    }

    private String dnFromUsername(String username) {
        String usernameDN = String.format("%s=%s,%s", principalRDTag, username, principalSuffix);
        log.info(String.format("translated: '%s' to: '%s'", username, usernameDN));
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
        Pair<Set<String>, Set<String>> rolesAndPermissions;
        try {
            rolesAndPermissions = getRolesAndPermissionsForUser(ldapConn, usernameDN);
            for (String role : rolesAndPermissions.a)
                log.info(String.format("user %s has role %s", username, role));
            for (String role : rolesAndPermissions.b)
                log.info(String.format("user %s has permission %s", username, role));
            
        } finally {
            ldapConn.close();
        }
        SimpleAuthorizationInfo retValue = new SimpleAuthorizationInfo(rolesAndPermissions.a);
        retValue. addStringPermissions(rolesAndPermissions.b);
        return retValue;
        // TODO: will have to recursively add groups for permissions as well
        // TODO: will have to replace the DN names of groups with a mapped role and privillige name by dropping the suffix
    }


    private Pair<Set<String>, Set<String>> getRolesAndPermissionsForUser(LDAPConnection ldapConn, String usernameDN) throws LDAPException {
        // 1st implementation: we don't translate the groups into roles in any manner
        // or do any sort of clever traversal routine to distinguish between roles (i.e. groups having another group 
        // as a child) and permissions (i.e. groups not having another group as a child)
        // return getGroupsWithUser(ldapConn, usernameDN);
        return getRecursiveFatherAndChildlessGroupsOfUser(ldapConn, usernameDN);
    }


    @Deprecated // simplified implementation which I don't use any more
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

    private Pair<Set<String>, Set<String>> getRecursiveFatherAndChildlessGroupsOfUser(LDAPConnection conn, String userDN)
        throws LDAPSearchException
    {
        Filter both               = Filter.createANDFilter(
                                        Filter.createEqualityFilter("objectclass", "orclGroup"),
                                        Filter.createEqualityFilter("uniqueMember", userDN)
                                    );
        SearchRequest sr = new SearchRequest(searchBase, SearchScope.SUB, both, "cn");

        SearchResult searchResult = conn.search(sr);
        log.debug(String.format("User '%s' is a direct member of %d groups", userDN, searchResult.getEntryCount()));
        Set<String> fatherGroups     = new LinkedHashSet<String>();
        Set<String> childlessGroups  = new LinkedHashSet<String>();
        for (SearchResultEntry entry : searchResult.getSearchEntries())
            recursivelyAddGroups(conn, fatherGroups, childlessGroups, entry.getDN());
        int numOfFatherGroups     = fatherGroups.size();
        int numOfChildlessGroups  = childlessGroups.size(); 
        int numOfDirectGroups     = searchResult.getEntryCount();
        int numOfIndirectGroups   = numOfFatherGroups + numOfChildlessGroups - numOfDirectGroups;
        log.trace(String.format("User '%s' is direct member of %d groups and indirect member of %d groups; of "+
                                "these, %d are role-groups and %d are permission-groups"
                                , userDN, numOfDirectGroups, numOfIndirectGroups, numOfFatherGroups, numOfChildlessGroups));
        return Pair.create(fatherGroups, childlessGroups);
    }

    private boolean isGroup(String dn) {
        String groupsSubtree = "cn=Groups,dc=neuropublic,dc=gr";
        return dn.toLowerCase().endsWith(groupsSubtree.toLowerCase());
    }

    private void recursivelyAddGroups(LDAPConnection conn, Set<String> fatherGroups, Set<String> childlessGroups, String groupDN)
        throws LDAPSearchException
    {
        log.info("looking for a group under base: "+groupDN);
        Filter sillyFilterInLieuOfAnAlwaysTrueFilter = Filter.createPresenceFilter("cn");
        SearchRequest sr = new SearchRequest(groupDN, SearchScope.BASE, sillyFilterInLieuOfAnAlwaysTrueFilter, "uniquemember");
        SearchResult searchResult = conn.search(sr);
        List<SearchResultEntry> searchResultEntries = searchResult.getSearchEntries();
        if (searchResultEntries.size()!=1) throw new RuntimeException(searchResultEntries.size()+" is not equal to 1");
        SearchResultEntry entry = searchResultEntries.get(0);
        Attribute uniqueMembers = entry.getAttribute("uniquemember");
        if (uniqueMembers != null) {
            String[] uniqueMembersStrArray = uniqueMembers.getValues();
            boolean foundOneChildGroup = false;
            for (String member : uniqueMembersStrArray) {
                log.info("examining: "+member);
                if (isGroup(member)) {
                    foundOneChildGroup = true;
                    recursivelyAddGroups(conn, fatherGroups, childlessGroups, member);
                }
            }
            if (roleOrPrivilligeName(groupDN)!=null) {
                if (foundOneChildGroup)
                    fatherGroups.add(roleOrPrivilligeName(groupDN));
                else
                    childlessGroups.add(roleOrPrivilligeName(groupDN));
            }
        } else {
            log.trace("attribute 'uniquemember' for group '"+groupDN+"' is null");
            if (roleOrPrivilligeName(groupDN)!=null)
                childlessGroups.add(roleOrPrivilligeName(groupDN));
        }
    }

    private String extractRDfromDN(String dn) {
        String firstComponent = dn.split(",")[0];
        final String ASSUMED_PREFFIX = rolesAndPrivilligesRDTag+"=";
        if (!firstComponent.startsWith(ASSUMED_PREFFIX)) throw new RuntimeException(dn);
        return firstComponent.replaceFirst(ASSUMED_PREFFIX, "");
    }

    private String roleOrPrivilligeName(String groupDN) {
        if (this.reportRolesAndPrivilligesWithRDOnly) {
            if (groupDN.endsWith(rolesAndPrivilligesSuffix))
                return extractRDfromDN(groupDN);
            else return null;
        }
        else return groupDN;
    }



}
