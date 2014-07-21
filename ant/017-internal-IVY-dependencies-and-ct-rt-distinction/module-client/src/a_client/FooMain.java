package a_client;

import org.apache.commons.lang3.StringUtils;


import a.A;
import b.B;

public class FooMain {

    public static void main(String args[]) throws Exception {
        System.out.println(StringUtils.join( new String[]{"hi", "world"}, " "));
    }
}