package gr.neuropublic.persutil;

import mutil.base.Pair;

public class AndComponent extends NonValueBearingJPQLComponent {
    @Override
    public String queryCompNoParameter() {
        return " and";
    }
}