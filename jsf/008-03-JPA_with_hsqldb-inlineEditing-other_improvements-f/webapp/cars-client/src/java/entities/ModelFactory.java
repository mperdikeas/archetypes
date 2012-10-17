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
public class ModelFactory implements Serializable {
    

    // @Column(name="MODEL", nullable=false)
    String model;

    @Column(name="FACTORY_NAME", nullable=false)
    String factory;

    public String getFactory()               { return factory         ; }
    public void   setFactory(String factory) { this.factory = factory ; }


    private static final Logger l = Logger.getLogger(ModelFactory.class.getName());

    @Override
    public boolean equals(Object otherO) {
        if (!(otherO instanceof ModelFactory)) return false;
        ModelFactory other = (ModelFactory) otherO;
        if (this.model!=null) {
            if (other.model==null)               return false;
            if (!this.model.equals(other.model)) return false;
        }
        if (this.factory!=null) {
            if (other.factory==null)               return false;
            if (!this.factory.equals(other.factory)) return false;
        }
        return true;
    }

    public String toString() {
        return "["+model+","+factory+"]";
    }

}
