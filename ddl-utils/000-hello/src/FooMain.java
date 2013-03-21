import javax.sql.DataSource;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Database;

public class FooMain {

public Database readDatabase(DataSource dataSource) {
    Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
    return platform.readModelFromDatabase("model");
}

public static void main(String args[]) {
    System.out.println("foo");
}

}