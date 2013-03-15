import org.stringtemplate.v4.*;

 
public class FooMain {


    public static void main(String[] args) {
        STGroup javaclass = new STGroupFile("templates/javaclass.stg");
        ST boo = javaclass.getInstanceOf("boo");
        System.out.println(boo.render());
    }



}