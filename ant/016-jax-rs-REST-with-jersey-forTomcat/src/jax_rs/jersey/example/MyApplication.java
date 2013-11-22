package jax_rs.jersey.example;

import java.util.Set;
import java.util.HashSet;


public class MyApplication extends javax.ws.rs.core.Application {
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(SimpleExampleController.class);
        return s;
    }
}