package gr.neuropublic.persutil;

import java.lang.annotation.Annotation;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;

import mutil.base.Pair;

public class PersRTTIUtil {
/*
    public static void main(String args[]) {
        for (Pair<Field, String> field : getColumnFields(Customer.class)) {
            System.out.println ("field : "+field.a.getName() + " has column name: "+field.b);
        }
        System.out.println("***********");
        System.out.println("surname column is: "+getColumnNameOfField(Customer.class, "surname"));
        System.out.println("doesnotexist column is: "+getColumnNameOfField(Customer.class, "doesnotexist"));
        int ITER = 100;
        long start = System.currentTimeMillis();
        for (int i = 0 ; i < ITER ; i++) 
            getColumnNameOfField(Customer.class, "surname");
        long end = System.currentTimeMillis();
        System.out.println(ITER +" iterations in " + ((end-start)/1000.0)+ " secs");

        System.out.println("query is: "+Select.from(Customer.class).where().f("name").is("Bill").and().f("surname").is("Smith").end().getQuery());

        System.out.println("********************************************************************************");
        System.out.println("2nd query is:"+Select.from(Customer.class).where().f("name").is("bill")
                                                              .and().f("surname").is("Archer")
                                                              .and().f("mncId.name").is("New York")
                           .and().f("employee").is(true).end().getQuery());
    }
*/
    public static String extractColumnName(Annotation annotation) {
        if (annotation instanceof Column) {
            Column column = (Column) annotation;
            return column.name();
        }
        else if (annotation instanceof JoinColumn) {
            JoinColumn joinColumn = (JoinColumn) annotation;
            return joinColumn.name();
        }
        else throw new RuntimeException();
    }

    public static List<Pair<Field, String>> getColumnFields(Class klass) {
        List<Pair<Field, String>> retValue = new ArrayList();
        for (Field field : klass.getDeclaredFields()) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if ((annotation instanceof Column)||(annotation instanceof JoinColumn)) {
                    String columnName = extractColumnName(annotation);            
                    retValue.add(Pair.create(field, columnName));
                    break;
                }
            }
        }
        return retValue;
    }

    public static String getColumnNameOfField(Class klass, String fieldName) {
        for (Pair<Field, String> fieldColumn : getColumnFields(klass)) {
            if (fieldColumn.a.getName().equals(fieldName))
                return fieldColumn.b;
        }
        return null;
    }
    
    public static boolean fieldExistsAndIsAColumn(Class klass, String fieldName) {
        for (Pair<Field, String> fieldColumn : getColumnFields(klass)) {
            if (fieldColumn.a.getName().equals(fieldName))
                return true;
        }
        return false;
    }
        
}
