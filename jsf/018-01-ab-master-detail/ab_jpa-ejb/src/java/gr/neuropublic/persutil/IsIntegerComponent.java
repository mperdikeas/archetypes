package gr.neuropublic.persutil;

import org.apache.commons.lang3.RandomStringUtils;

import mutil.base.Pair;

public class IsIntegerComponent implements IIsComponent, IJPQLQueryComponent {
    private Integer value;
    public IsIntegerComponent (Integer value) {
        this.value = value;
    }

    // do we need that ?
    // public Integer getValue() {return value;}

    @Override
    public String queryCompNoParameter() {return " = "+value;}

    @Override
    public Pair<String, Pair<String, Object>> queryCompAndParameter() {
        String paramName = RandomStringUtils.randomAlphabetic(20);
        System.out.println("parameter name chosen as: "+paramName);
        String paramQuery="=:"+paramName;
        Pair<String, Object> param = Pair.create(paramName, (Object) value);
        return Pair.create(paramQuery, param);
    }
}