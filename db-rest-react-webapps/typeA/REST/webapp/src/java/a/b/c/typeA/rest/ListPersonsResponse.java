package a.b.c.typeA.rest;

import java.util.List;

import a.b.c.typeA.dbdal.Person;


public class ListPersonsResponse {
    public String      request;
    public String      responseDate;
    public List<Person> people;


    public ListPersonsResponse(String request,
                               String responseDate,
                               List<Person> people) {
        this.request      = request;
        this.responseDate = responseDate;
        this.people       = people;
    }
}
