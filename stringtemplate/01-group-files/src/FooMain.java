import org.stringtemplate.v4.*;

 
public class FooMain {

    private static String[] greetingTypes = {"formal", "casual"};

    public static void main(String[] args) {
        STGroup greetGroup = new STGroupFile("templates/greetings.stg");


        for (String greetingType : greetingTypes)
            System.out.println(String.format("%s\n", renderGreeting(greetGroup, greetingType, "Mike", "24.XII.2013")));
    }


    private static String renderGreeting(STGroup greetGroup, String greetingType, String name, String date) {
        ST greeting = greetGroup.getInstanceOf(greetingType);
        greeting.add("name", name);
        greeting.add("date", date);
        return greeting.render();
    }
}