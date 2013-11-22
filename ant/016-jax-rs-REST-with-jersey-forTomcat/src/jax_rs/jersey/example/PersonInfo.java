package jax_rs.jersey.example;

import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;  

@XmlRootElement(name = "employee") // not having this annotation mysteriously throws the following exception:
                                   // javax.servlet.ServletException: org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException: MessageBodyWriter not found for media type=application/xml, type=class jax_rs.jersey.example.PersonInfo, genericType=class jax_rs.jersey.example.PersonInfo.
public class PersonInfo {
    private String name;
    private String surname;
    private int yearOfBirth;

    public PersonInfo() {
    }

    public PersonInfo(String name, String surname, int yearOfBirth) {
        this.name = name;
        this.surname = surname;
        this.yearOfBirth = yearOfBirth;
    }
    
    @XmlElement
    public String getName() { return this.name; }
    public void setName(String name) {this.name = name;}

    @XmlElement
    public String getSurname() { return this.surname; }
    public void setSurname(String surname) {this.surname = surname;}

    @XmlElement
    public int getYearOfBirth() { return this.yearOfBirth; }
    public void setYearOfBirth(int yearOfBirth) { this.yearOfBirth = yearOfBirth;}

}
