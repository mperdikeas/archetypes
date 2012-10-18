package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.logging.Logger;

@Embeddable
public class ModelSpec implements Serializable {
    

    // @Column(name="MODEL", nullable=false)
    String model;

    // @Column(name="SPEC", nullable=false)
    String spec;

    public String getSpec()            { return spec ;      }
    public void   setSpec(String spec) { this.spec = spec ; }

    public String getFooKey() { return model+spec; }
    private static final Logger l = Logger.getLogger(ModelSpec.class.getName());

    @Override
    public boolean equals(Object otherO) {
        if (!(otherO instanceof ModelSpec)) return false;
        ModelSpec other = (ModelSpec) otherO;
        l.info("comparing "+this+" against "+other);
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

    public String toString() {
        return "["+model+","+spec+"]";
    }

}
