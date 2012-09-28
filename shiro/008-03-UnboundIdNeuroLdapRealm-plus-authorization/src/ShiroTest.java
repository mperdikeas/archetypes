import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroTest {

    private static Logger logger = LoggerFactory.getLogger(ShiroTest.class);

    public static void main(String[] args) {

        // Using the IniSecurityManagerFactory, which will use the
        // activated INI file as the security file.
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("activated.ini");

        // Setting up the SecurityManager...
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject user = SecurityUtils.getSubject();
        logger.trace("trace level logging");
        logger.info("User is authenticated:  " + user.isAuthenticated());
        logger.debug("debug level logging");
        logger.info("info level logging");

        //both below lines work (but maybe you'll have to target a different LDAP repository)
      //UsernamePasswordToken token = new UsernamePasswordToken("cn=orcladmin,cn=Users,dc=neuropublic,dc=gr", "welcome1");
      //UsernamePasswordToken token = new UsernamePasswordToken("cn=developer,cn=Users,dc=neuropublic,dc=gr", "12345678");
        UsernamePasswordToken token = new UsernamePasswordToken("developer", "12345678");

        user.login(token);
        logger.info("User is authenticated:  " + user.isAuthenticated());
        user.checkRole("cn=Foo,cn=Groups,dc=neuropublic,dc=gr");
    }
}