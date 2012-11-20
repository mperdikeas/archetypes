package gr.neuropublic.persutil;

import mutil.base.Pair;

public interface IJPQLQueryComponent {
    public String queryCompNoParameter();
    public Pair<String, Pair<String, Object>> queryCompAndParameter();
 }