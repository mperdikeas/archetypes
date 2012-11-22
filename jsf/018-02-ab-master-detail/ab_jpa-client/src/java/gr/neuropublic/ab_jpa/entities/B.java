package gr.neuropublic.ab_jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.*;
import javax.validation.constraints.Null;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import gr.neuropublic.ab_jpa.entitiesBase.BBase;


@Entity
@Table(name = "b", schema="public")
public class B  extends BBase {

    public B() {
        super();
    }
    public B(Integer id) {
        super(id);
    }
    public B(Integer id, String b1) {
        super(id, b1);
    }
    public B(String b1, A a) {
        super(b1, a);
    }

    // Add your custom methods here
    // This file will not be regenarated, so it is safe to edit it.
}