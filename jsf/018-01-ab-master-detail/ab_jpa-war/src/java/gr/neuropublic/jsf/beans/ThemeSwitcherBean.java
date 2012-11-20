package gr.neuropublic.jsf.beans;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is used as a managed bean to hold current application theme
 * and available theme choices
 * 
 * {themes} property is populated through xml injection
 * 
 * @author g_dimitriadis
 */
public class ThemeSwitcherBean implements Serializable {

    private Map<String, String> themes =  new TreeMap<String, String>();
    private String current;

    public Map<String, String> getThemes() {
        return themes;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
    
    public void saveTheme() {
    }
}