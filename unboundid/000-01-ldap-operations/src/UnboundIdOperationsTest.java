import com.unboundid.ldap.sdk.*;


public class UnboundIdOperationsTest {
    public static void main(String[] args) throws Exception {
        String host   = "172.31.128.30";
        int    port   = 389;
        String bindDN = "cn=orcladmin,cn=Users,dc=neuropublic,dc=gr";
        String passwd = "welcome1";
        LDAPConnection conn = new LDAPConnection(host, port, bindDN, passwd);
        System.out.println("connection established");
        printUsers(conn);
        conn.close();
        System.out.println("connection closed");
    }

    private static void printUsers(LDAPConnection conn) {
        Filter filter = Filter.createEqualityFilter("objectclass", "person");
        // possible SearchScope values include BASE, ONE, SUB and SUBORDINATE_SUBTREE
        SearchRequest searchRequest = new SearchRequest("dc=neuropublic,dc=gr", SearchScope.SUB, filter, "cn");
        try {
            SearchResult searchResult = conn.search(searchRequest);
            System.out.println(searchResult.getEntryCount()+" matching entries returned");
            for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                String name = entry.getAttributeValue("cn");
                System.out.println("name is :"+name);
            }
        } catch (LDAPSearchException lse) {
            System.out.println("search failed");
            lse.printStackTrace();
        }
    }
}

