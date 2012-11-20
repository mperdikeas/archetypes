package gr.neuropublic.persutil;

import java.util.logging.Logger;
import org.apache.commons.lang3.RandomStringUtils;

import mutil.base.Pair;

public class IsComponent implements IIsComponent, IJPQLQueryComponent {
    Logger l = Logger.getLogger(IsComponent.class.getName());
    private String value;
    public IsComponent(String value) {
        this.value = value;
    }
    public String getValue() {return value;}
    @Override
    public String queryCompNoParameter() {return " = '"+value+"'";}
    @Override
    public Pair<String, Pair<String, Object>> queryCompAndParameter() {
        String paramName = RandomStringUtils.randomAlphabetic(20);
        l.info("parameter name chosen as: "+paramName);
        String paramQuery="=:"+paramName;
        Pair<String, Object> param = Pair.create(paramName, (Object) value);
        return Pair.create(paramQuery, param);
    }
}