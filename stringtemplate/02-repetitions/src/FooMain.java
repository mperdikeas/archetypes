import org.stringtemplate.v4.*;

 
public class FooMain {


    public static void main(String[] args) {
        STGroup javaclass = new STGroupFile("templates/javaclass.stg");
        ST constructor = javaclass.getInstanceOf("constructor");
        constructor.add("name", "MyClass");
        constructor.add("paramlist", "int a, Boolean foo");
        ST body = javaclass.getInstanceOf("body");
        body.add("constructor", constructor.render());
        ST klass = javaclass.getInstanceOf("class");
        klass.add("name", "MyClass");
        klass.add("body", body.render());
        System.out.println(klass.render());
    }



}