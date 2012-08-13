package gr.neuropublic.neurojsfpilot.customerservice.backingBeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class CustomerBackingBean implements Serializable {
    private static final Logger l = Logger.getLogger(CustomerNameDlgBackingBean.class.getName());

    private int id;
    public int getId()        {return id;   }
    public void setId(int id) {this.id = id;}
    private String name;
    public String getName() {
        l.info(CustomerBackingBean.class.getName()+"#getName() returning: "+name);
        return name;
    }
    public void setName(String name) {
        l.info(CustomerBackingBean.class.getName()+"#setName("+name+")");
        this.name = name;
    }
    private String nameFromDialog;
    public  String getNameFromDialog() {return nameFromDialog;}
    public  void setNameFromDialog(String name) {this.name = name;}

    private String _hiddenName;
    public  String getTentativeName() {return _hiddenName;}
    public  void   setTentativeName(String tentative) {
        _hiddenName = tentative;
    }

    public void copyOver() {
        l.info("inside copyOver(), copying "+_hiddenName+" to "+name);
        name = _hiddenName;
    }
}