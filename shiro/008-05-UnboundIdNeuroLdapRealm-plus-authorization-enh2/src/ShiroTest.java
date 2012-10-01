import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroTest {

    private static Logger log = LoggerFactory.getLogger(ShiroTest.class);

    public static void main(String[] args) {

        // Using the IniSecurityManagerFactory, which will use the
        // activated INI file as the security file.
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("activated.ini");

        // Setting up the SecurityManager...
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject user = SecurityUtils.getSubject();

        log.info("User is authenticated:  " + user.isAuthenticated());

        //both below lines work (but maybe you'll have to target a different LDAP repository)
      //UsernamePasswordToken token = new UsernamePasswordToken("cn=orcladmin,cn=Users,dc=neuropublic,dc=gr", "welcome1");
      //UsernamePasswordToken token = new UsernamePasswordToken("cn=developer,cn=Users,dc=neuropublic,dc=gr", "12345678");
        UsernamePasswordToken token = new UsernamePasswordToken("developer", "12345678");

        try {
            user.login(token);
            log.info("User is authenticated:  " + user.isAuthenticated());
        } catch (AuthenticationException ae) {
            log.info("User fail to authenticate");
        }
        String roleName = null;
        boolean shiro_ini_is_configured_with_reportRolesAndPrivilligesWithRDOnly = true;
        if (shiro_ini_is_configured_with_reportRolesAndPrivilligesWithRDOnly)
            roleName = "Foo";
        else
            roleName = "cn=Foo,cn=Groups,dc=neuropublic,dc=gr";
        user.checkRole(roleName);
        

        String permissionName=null;
        if (shiro_ini_is_configured_with_reportRolesAndPrivilligesWithRDOnly)
            permissionName = "odee_αίτησηαναθεώρησης2012προσωρινώνδικαιωμάτων_δημιουργία";
        else
            permissionName = "cn=odee_αίτησηαναθεώρησης2012προσωρινώνδικαιωμάτων_δημιουργία,cn=groups,dc=neuropublic,dc=gr";
        user.checkPermission(permissionName);
        log.info("if you didn't see any exceptions I apparently managed to authorize user for the following:\n\trole = "
                 +roleName+"\n\tpermission = "+permissionName);
    }
}