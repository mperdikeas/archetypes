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
    


    ModelSpec modelSpec;
    String reviewer;

    public String getReviewer() {
        return reviewer;
    }

    private static final Logger l = Logger.getLogger(ModelSpecReviewer.class.getName());

    @Override
    public boolean equals(Object otherO) {
        if (!(otherO instanceof ModelSpecReviewer)) return false;
        ModelSpecReviewer other = (ModelSpecReviewer) otherO;
        l.info("comparing "+this+" against "+other);
        if (this.modelSpec!=null) {
            if (other.modelSpec==null)                      return false;
            if (!this.modelSpec.equals(other.modelSpec))        return false;
        }
        if (this.reviewer!=null) {
            if (other.reviewer==null)                   return false;
            if (!this.reviewer.equals(other.reviewer))    return false;
        }
        return true;
    }

    public String toString() {
        return "["+modelSpec+","+reviewer+"]";
    }

}
