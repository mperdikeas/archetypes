package gr.neuropublic.neurojsfpilot.customerservice.backingBeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class CustomerBackingBean implements Serializable {
    private int id;
    public int getId()        {return id;   }
    public void setId(int id) {this.id = id;}
    private String name;
    public String getName() {return name;}
    public void setName(String name) { this.name = name;}
    private String nameFromDialog;
    public String getNameFromDialog() {return nameFromDialog;}
    public void setNameFromDialog(String name) {this.name = name;}
}