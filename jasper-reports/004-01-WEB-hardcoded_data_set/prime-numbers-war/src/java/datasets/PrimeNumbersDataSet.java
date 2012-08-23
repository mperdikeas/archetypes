package datasets;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class PrimeNumbersDataSet implements JRDataSource {
  
    private Object[][] data = {
        { new Integer(2), "two"},
        { new Integer(3), "three"},
        { new Integer(5), "five"},
        { new Integer(7), "seven"},
        { new Integer(11), "eleven"},
        { new Integer(13), "thirteen"},
        { new Integer(17), "seventeen"}};

  
    private int index = -1;
  
    public PrimeNumbersDataSet() {
        super();
    }
  
    public boolean next() throws JRException {
        index++;
        return (index < data.length);
    }

    public Object getFieldValue(JRField field) throws JRException {
        Object value = null;

        String fieldName = field.getName();

        if (fieldName.equals("Number")) {
            value = data[index][0];
        } else if (fieldName.equals("Name")) {
            value = data[index][1];
        }

        return value;
    }
}