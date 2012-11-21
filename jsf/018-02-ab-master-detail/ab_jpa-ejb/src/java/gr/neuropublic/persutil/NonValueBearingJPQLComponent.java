package gr.neuropublic.persutil;

import mutil.base.Pair;

public abstract class NonValueBearingJPQLComponent implements IJPQLQueryComponent {

    @Override
    public Pair<String, Pair<String, Object>> queryCompAndParameter() {
        return Pair.create(queryCompNoParameter(), null);
    }


}