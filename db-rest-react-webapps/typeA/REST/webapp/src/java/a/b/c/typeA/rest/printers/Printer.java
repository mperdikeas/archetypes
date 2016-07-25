package a.b.c.typeA.rest.printer;

import java.util.List;


import mutil.json.JsonProvider;

import a.b.c.typeA.rest.ListPersonsResponse;


public class Printer {

    public static String print(ListPersonsResponse response) {
        return JsonProvider.toJson(response);
    }
}


