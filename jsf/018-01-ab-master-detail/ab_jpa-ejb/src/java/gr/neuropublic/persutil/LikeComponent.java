package gr.neuropublic.persutil;

import org.apache.commons.lang3.RandomStringUtils;

import mutil.base.Pair;

public class LikeComponent implements IIsComponent, IJPQLQueryComponent {
    private String value;
    public LikeComponent(String value) {
        this.value = value;
    }
    public String getValue() {return value;}
    @Override
    public String queryCompNoParameter() {return " like '"+value+"'";}
    @Override
    public Pair<String, Pair<String, Object>> queryCompAndParameter() {
        String paramName = RandomStringUtils.randomAlphabetic(20);
        System.out.println("parameter name chosen as: "+paramName);
        String paramQuery=" like :"+paramName;
        Pair<String, Object> param = Pair.create(paramName, (Object) value);
        return Pair.create(paramQuery, param);
    }
}