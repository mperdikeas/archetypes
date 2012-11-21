package gr.neuropublic.persutil;

import org.apache.commons.lang3.RandomStringUtils;

import mutil.base.Pair;

public class IsBooleanComponent implements IIsComponent, IJPQLQueryComponent {
    private Boolean value;
    public IsBooleanComponent(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {return value;}

    @Override
        public String queryCompNoParameter() {return " = "+((value==null)?"NULL-SHOULD-BE-ELIMINATED":value.toString());}

    @Override
    public Pair<String, Pair<String, Object>> queryCompAndParameter() {
        String paramName = RandomStringUtils.randomAlphabetic(20);
        System.out.println("parameter name chosen as: "+paramName);
        String paramQuery="=:"+paramName;
        Pair<String, Object> param = Pair.create(paramName, (Object) value);
        return Pair.create(paramQuery, param);
    }
}