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
public class ModelSpecReviewer implements Serializable {
    

    @Column(nullable=false)
    String model;
    @Column(nullable=false)
    String spec;

    @Column(name="SPEC_REVIEWER", nullable=false)
    String reviewer;

    /*
    public String getRevi()            { return spec ;      }
    public void   setSpec(String spec) { this.spec = spec ; }*/


    private static final Logger l = Logger.getLogger(ModelSpecReviewer.class.getName());

    @Override
    public boolean equals(Object otherO) {
        if (!(otherO instanceof ModelSpecReviewer)) return false;
        ModelSpecReviewer other = (ModelSpecReviewer) otherO;
        l.info("comparing "+this+" against "+other);
        if (this.model!=null) {
            if (other.model==null)                      return false;
            if (!this.model.equals(other.model))        return false;
        }
        if (this.spec!=null) {
            if (other.spec==null)                       return false;
            if (!this.spec.equals(other.spec))          return false;
        }
        if (this.reviewer!=null) {
            if (other.reviewer==null)                   return false;
            if (!this.reviewer.equals(other.reviewer))    return false;
        }
        return true;
    }

    public String toString() {
        return "["+model+","+spec+","+reviewer+"]";
    }

}
