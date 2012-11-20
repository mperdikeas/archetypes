package gr.neuropublic.TestApp.entitiesBase;

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

import gr.neuropublic.TestApp.entities.*;
import gr.neuropublic.base.Identifiable;

/*


*/

@MappedSuperclass
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "A.findAll", query = "SELECT x FROM A x"),
@NamedQuery(name = "A.delAll", query = "DELETE FROM A x"),
@NamedQuery(name = "A.findById", query = "SELECT x FROM A x WHERE x.id = :id"),
@NamedQuery(name = "A.delById", query = "DELETE FROM A x WHERE x.id = :id"),
@NamedQuery(name = "A.findByA1", query = "SELECT x FROM A x WHERE x.a1 = :a1"),
@NamedQuery(name = "A.delByA1", query = "DELETE FROM A x WHERE x.a1 = :a1")})
public abstract class ABase implements Serializable,Identifiable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="a_id")
    //@SequenceGenerator(name="a_id", sequenceName="a_id_seq", allocationSize=1)
    protected Integer id;


    @Basic(optional = true)
    @Size(min = 1, max = 120)
    @Column(name = "a1")
    protected String a1;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "a", orphanRemoval=true)
    protected Collection<B> baCollection;



    public ABase() {
    }

    public ABase(Integer id) {
        this.id = id;
    }

    public ABase(Integer id, String a1) {
        this.id = id;
        this.a1 = a1;
    }

    public ABase(String a1) {
        this.a1 = a1;
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
    public String getA1() {
        return a1;
    }
    
    public void setA1(String a1) {
        this.a1 = a1;
    }
    @XmlTransient
    public Collection<B> getBaCollection() {
        return baCollection;
    }
    
    public void setBaCollection(Collection<B> baCollection) {
        this.baCollection = baCollection;
    }
    
    public void removeBaCollection(B child) {
        if (child != null)
            child.setA(null);
    }
    
    public void addBaCollection(B child) {
        if (child != null)
            child.setA((A)this);
    }
    
    void internalRemoveBaCollection(B child) {
        if (child != null)
            this.baCollection.remove(child);
    }
    
    void internalAddBaCollection(B child) {
        if (child != null)
            this.baCollection.add(child);
    }



    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }



    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ABase)) {
            return false;
        }
        ABase other = (ABase) object;
        if (this.id == null || other.id == null) 
            return false;
        
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "gr.neuropublic.TestApp.entitiesBase.A[ id=" + id + " ]";
    }
    
}
