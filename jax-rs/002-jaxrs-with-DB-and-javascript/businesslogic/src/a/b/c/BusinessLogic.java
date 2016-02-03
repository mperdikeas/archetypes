package a.b.c;

import d.e.f.IDBDAL;



public class BusinessLogic implements IBusinessLogic {


    @Override    
    public String retrieve(IDBDAL dbAPI, String key) {
        return dbAPI.retrieve(key);
    }


}
