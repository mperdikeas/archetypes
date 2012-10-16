package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Embeddable
public class ModelSpec implements Serializable {
    
    // @Column(name="MODEL", nullable=false)
    String model;
    // public String getModel() { return model; }
    // public void setModel(String model) { this.model = model; }

    // @Column(name="SPEC", nullable=false)
    String spec;

    public String getSpec() { return spec ; }

    @Override
    public boolean equals(Object otherO) {
        if (!(otherO instanceof ModelSpec)) return false;
        ModelSpec other = (ModelSpec) otherO;
        if (this.model!=null) {
            if (other.model==null)               return false;
            if (!this.model.equals(other.model)) return false;
        }
        if (this.spec!=null) {
            if (other.spec==null)               return false;
            if (!this.spec.equals(other.spec)) return false;
        }
        return true;
    }
    // public String getSpec() { return spec; }
    // public void setSpec(String spec) { this.spec = spec; }
    
    /*public ModelSpec(String model, String spec) {
        this.model = model;
        this.spec  = spec;
        }*/

}
