package gr.neuropublic.persutil;

import java.util.logging.Logger;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

import mutil.base.Pair;

// Inclusive
public class AfterComponent implements IIsComponent, IJPQLQueryComponent {
    Logger l = Logger.getLogger(AfterComponent.class.getName());
    private Date value;

    public AfterComponent(Date value) {
        this.value = value;
    }

    public Date getValue() {return this.value;}
    @Override
        public String queryCompNoParameter() {return " >= "+((value==null)?"NULL-SHOULD-BE-ELIMINATED":(new java.sql.Date(value.getTime())).toString());}
    @Override
    public Pair<String, Pair<String, Object>> queryCompAndParameter() {
        String paramName = RandomStringUtils.randomAlphabetic(20);
        String paramQuery=">= :"+paramName;
        Pair<String, Object> param = Pair.create(paramName, (Object) value);
        return Pair.create(paramQuery, param);
    }
}