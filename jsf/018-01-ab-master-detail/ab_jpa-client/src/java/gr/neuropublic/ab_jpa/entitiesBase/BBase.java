package gr.neuropublic.ab_jpa.entitiesBase;

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

import gr.neuropublic.ab_jpa.entities.*;
import gr.neuropublic.base.Identifiable;

/*


*/

@MappedSuperclass
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "B.findAll", query = "SELECT x FROM B x"),
@NamedQuery(name = "B.delAll", query = "DELETE FROM B x"),
@NamedQuery(name = "B.findById", query = "SELECT x FROM B x WHERE x.id = :id"),
@NamedQuery(name = "B.delById", query = "DELETE FROM B x WHERE x.id = :id"),
@NamedQuery(name = "B.findByB1", query = "SELECT x FROM B x WHERE x.b1 = :b1"),
@NamedQuery(name = "B.delByB1", query = "DELETE FROM B x WHERE x.b1 = :b1"),
@NamedQuery(name = "B.findByA", query = "SELECT x FROM B x WHERE x.a = :a"),
@NamedQuery(name = "B.delByA", query = "DELETE FROM B x WHERE x.a = :a"),
@NamedQuery(name = "B.findB", query = "SELECT x FROM B x WHERE (x.b1 like :b1) AND (x.a = :a)")})
public abstract class BBase implements Serializable,Identifiable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="b_id")
    //@SequenceGenerator(name="b_id", sequenceName="b_id_seq", allocationSize=1)
    protected Integer id;


    @Basic(optional = true)
    @Size(min = 1, max = 120)
    @Column(name = "b1")
    protected String b1;


    @JoinColumn(name = "a", referencedColumnName = "id")
    @ManyToOne(optional = false)
    protected A a;



    public BBase() {
    }

    public BBase(Integer id) {
        this.id = id;
    }

    public BBase(Integer id, String b1) {
        this.id = id;
        this.b1 = b1;
    }

    public BBase(String b1, A a) {
        this.b1 = b1;
        this.a = a;
    }

    /*
    */
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    /*
    */
    public String getB1() {
        return b1;
    }
    
    public void setB1(String b1) {
        this.b1 = b1;
    }
    /*
    */
    public A getA() {
        return a;
    }
    
    public void setA(A a) {
    //    if (this.a != null) 
    //        this.a.internalRemoveBaCollection(this);
        this.a = a;
    //    if (a != null)
    //        a.internalAddBaCollection(this);
    }


    public String getA_a1() {
        if (getA()!=null)
            return getA().getA1();
        return null;
    }
    public void setA_a1(String a1) {
        if (getA()!=null)
            getA().setA1(a1);
    }

    @PreRemove
    public void preRemove() {
        setA(null);
    } 
    
    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }



    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BBase)) {
            return false;
        }
        BBase other = (BBase) object;
        if (this.id == null || other.id == null) 
            return false;
        
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "gr.neuropublic.ab_jpa.entitiesBase.B[ id=" + id + " ]";
    }
    
}
