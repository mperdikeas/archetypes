import com.unboundid.ldap.sdk.LDAPConnection;

public class UnboundIdLoginTest {
    public static void main(String[] args) throws Exception {
        String host   = "172.31.128.30";
        int    port   = 389;
        String bindDN = "cn=orcladmin,cn=Users,dc=neuropublic,dc=gr";
        String passwd = "welcome1";
        LDAPConnection conn = new LDAPConnection(host, port, bindDN, passwd);
        System.out.println("connection established");
        conn.close();
        System.out.println("connection closed");
    }
}

