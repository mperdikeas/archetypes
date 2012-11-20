package gr.neuropublic.persutil;

import mutil.base.Pair;

public class WhereComponent extends NonValueBearingJPQLComponent {
    
    @Override
    public String queryCompNoParameter() {
        return " WHERE";
    }

}