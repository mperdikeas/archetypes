package entities;

import java.util.logging.Logger;
import java.io.Serializable;
import java.util.Set;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

// hibernate-specific option
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "A")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "A.findAll"    , query = "SELECT x FROM A x"),
    @NamedQuery(name = "A.findById", query = "SELECT x FROM A x WHERE x.id = :MODEL")
        }) 
public class A implements Serializable {
    
    private static final long serialVersionUID = 1L;
    public static int instances = 0;

    public A() {
        instances++;
    }

    private static final Logger l = Logger.getLogger(A.class.getName());

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "A1")
    private String a1;


    // @LazyCollection(LazyCollectionOption.FALSE) // I don't use that
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aId", fetch=FetchType.EAGER)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<B> bCollection;

    public Set<B> getBCollection() { 
        return bCollection;
    }
    public void setBCollection(Set<B> bCollection) {
        this.bCollection = bCollection;
    }


    public A(Integer id, String a1) {
        this.id = id;
        this.a1 = a1;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public void removeB(B b) {
        b.setAId(null);
    }
    public void addB(B b) {
        b.setAId(this);
    }
    void internalRemoveB(B b) {
        this.bCollection.remove(b);
    }
    void internalAddB(B b) {
        this.bCollection.add(b);
    }



    @Override
    public boolean equals(Object object) {
        if (!(object instanceof A)) {
            return false;
        }
        A other = (A) object;
        if ((this.id == null && other.id != null) || 
            (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.a1 == null && other.a1 != null) || 
            (this.a1 != null && !this.a1.equals(other.a1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return A.class.getName()+"[ id = " + id + ", a1 = "+a1+" ]";
    }

    
}
