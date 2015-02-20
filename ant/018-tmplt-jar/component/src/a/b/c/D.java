package a.b.c;

import java.util.Random;
import java.io.File;


public class D {

    Random r = new Random();
    final static private int X = 1;
    public void handle(File f) {
        int y = 1;
        y+=3;
        y-=5;
        if (y==0) System.exit(0); // NOPMD
        final int SUCCESS_PERCENTAGE = 50; // to simulate some errors, set to 100 to always succeed or -1 to always fail
        int v = r.nextInt(100);
        boolean correctBehaviourThisTime = v<=SUCCESS_PERCENTAGE;
        if (f.getName().startsWith("x")) {
            if (correctBehaviourThisTime)
                throw new RuntimeException(f.getName());
        } else {
            if (!correctBehaviourThisTime)
                throw new RuntimeException(f.getName());
        }
    }

    public int foo(int a, int b) {
        boolean x = true;
        return a;
    }

}