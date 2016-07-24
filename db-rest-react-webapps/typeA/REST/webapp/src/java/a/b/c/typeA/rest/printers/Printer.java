package a.b.c.typeA.rest.printer;

import javax.xml.datatype.DatatypeConfigurationException;

import mutil.json.JsonProvider;


public class Printer {

    public static String print(String request, List<Person> people) {
        try {
            String rv =  JsonProvider.toJson(new ListPersonsResponse(request
                                                                     , TimeUtils.nowXMLGregorianZRnd().toString()
                                                                     , people));
            return rv;
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}


class ListPersonsResponse {
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
