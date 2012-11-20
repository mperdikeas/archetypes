package gr.neuropublic.TestApp.entities;

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

import gr.neuropublic.TestApp.entitiesBase.ABase;


@Entity
@Table(name = "a")
public class A  extends ABase {

    public A() {
        super();
    }
    public A(Integer id) {
        super(id);
    }
    public A(Integer id, String a1) {
        super(id, a1);
    }
    public A(String a1) {
        super(a1);
    }

    // Add your custom methods here
    // This file will not be regenarated, so it is safe to edit it.
}