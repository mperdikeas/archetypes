import com.unboundid.ldap.sdk.*;


public class UnboundIdOperationsTest {
    public static void main(String[] args) throws Exception {
        String host   = "172.31.128.30";
        int    port   = 389;
        String bindDN = "cn=orcladmin,cn=Users,dc=neuropublic,dc=gr";
        String passwd = "welcome1";
        LDAPConnection conn = new LDAPConnection(host, port, bindDN, passwd);
        System.out.println("connection established");
        System.out.println("/*************** GROUPS ***************\\");
        printGroups(conn);
        System.out.println("/*************** USERS ***************\\");
        printUsers(conn);
        System.out.println("/*************** FIND ALL GROUPS TO WHICH A SPECIFIC USER BELONGS ****************\\");
        printGroupsWithUser(conn, "cn=developer,cn=Users,dc=neuropublic,dc=gr");
        conn.close();
        System.out.println("connection closed");
    }

    private static void printGroups(LDAPConnection conn) {
        Filter filter = Filter.createEqualityFilter("objectclass", "orclGroup");
        // possible SearchScope values include BASE, ONE, SUB and SUBORDINATE_SUBTREE
        SearchRequest searchRequest = new SearchRequest("dc=neuropublic,dc=gr", SearchScope.SUB, filter, "cn");
        printNames(conn, searchRequest, "cn");
    }

    private static void printUsers(LDAPConnection conn) {
        Filter filter = Filter.createEqualityFilter("objectclass", "person");
        // possible SearchScope values include BASE, ONE, SUB and SUBORDINATE_SUBTREE
        SearchRequest searchRequest = new SearchRequest("dc=neuropublic,dc=gr", SearchScope.SUB, filter, "cn");
        printNames(conn, searchRequest, "cn");
    }

    private static void printNames(LDAPConnection conn, SearchRequest searchRequest, String attributeName) {
        try {
            SearchResult searchResult = conn.search(searchRequest);
            System.out.println(searchResult.getEntryCount()+" matching entries returned");
            for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                String name = entry.getAttributeValue(attributeName);
                System.out.println(String.format("%s -> value of attribute '%s' is: '%s'", entry.getDN(), attributeName, name));
            }
        } catch (LDAPSearchException lse) {
            System.out.println("search failed");
            lse.printStackTrace();
        }
    }

    private static void printGroupsWithUser(LDAPConnection conn, String userDN) {
        Filter groupfilter        = Filter.createEqualityFilter("objectclass", "orclGroup");
        Filter uniqueMemberFilter = Filter.createEqualityFilter("uniqueMember", userDN);
        Filter both               = Filter.createANDFilter(groupfilter, uniqueMemberFilter);
        SearchRequest sr = new SearchRequest("dc=neuropublic,dc=gr", SearchScope.SUB, both, "cn");
        printNames(conn, sr, "cn");
    }
}

