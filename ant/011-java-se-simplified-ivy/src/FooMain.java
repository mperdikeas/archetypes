import org.apache.commons.lang3.StringUtils;


public class FooMain {

    public static void main(String args[]) throws Exception {
        System.out.println(StringUtils.join( new String[]{"hello", "world"}, " "));
        System.out.printf("\n\n\n\n\n\n\n\n\t\t\t\t\tThis is going to exit with a value of [3] just to showcase the transfer of the exit code to Ant.\n\n\n\n\n\n");
        Class<?> klass = Class.forName("org.postgresql.Driver");
        System.exit(3);
    }
}