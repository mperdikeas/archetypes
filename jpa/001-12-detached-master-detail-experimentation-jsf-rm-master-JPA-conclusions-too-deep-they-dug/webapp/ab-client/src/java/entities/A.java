package entities;

import java.util.logging.Logger;
import java.io.Serializable;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

// hibernate-specific option
// import org.hibernate.annotations.LazyCollection;
// import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "A")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "A.findAll"    , query = "SELECT x FROM A x"),
    @NamedQuery(name = "A.findById", query = "SELECT x FROM A x WHERE x.id = :MODEL")
        }) 
public class A implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private static final Logger l = Logger.getLogger(A.class.getName());

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public Integer getId() { return id; }

    @Basic(optional = false)
    @NotNull
    @Column(name = "A1")
    private String a1;


    // @LazyCollection(LazyCollectionOption.FALSE) // I don't use that
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "aId", fetch=FetchType.EAGER, orphanRemoval=true) // line-46
    // For both methods of removal to work, the master entity (A) must have all CascadeTypes except DETACH
    // (in order to allow the detachment of the master entity in the logic of BFacade:line-46).
    // Otherwise, if the detachement logic in BFacade:line-46 is not present then the removal using EntityManager:remove()
    // will fail because the merge of the B entity will result in the attachment of the A entity (this can't be controlled
    // by use of CascadeType as this is a reference, not a collection) whereupon the A entity will be fetched from the database
    // (including, in its collection the B entity that was pruned) in the context of the EntityManager (as a managed entity).
    // Then during commit() when the EntityManager in effect calls "persist" on all managed entities [1], the pruned B entity
    // will be resurrected (but also be marked for removal but apparently persist wins). The resurrection is due to the 
    // CascadeType.PERSIST in the A entity's collection (which can't be simply removed cause it creates side-effects elsewhere).
    // So the only thing to do is to explicitly DETACH the A entity from the EntityManager whereupon it is important that A
    // doesn't have CascadeType.DETACH (see code and comments in BFacade line-46).
    //
    // References:
    // [1]  "Apress.Pro.JPA-2 Master the Java Persistence API" book, pg. 157 (synchronization with the Database)
    //      - and -
    // [2]  http://stackoverflow.com/questions/13145045/jpa-hibernate-removing-child-entities
    //
    private Set<B> bCollection;

    public Set<B> getBCollection() {
        l.info("returning B collection of type: "+ bCollection.getClass().getName()); // in case of FetchType.LAZY it is a: org.hibernate.collection.internal.PersistentSet. In the case of FetchType.EAGER it is the same class.
        return bCollection;
    }
    public void setBCollection(Set<B> bCollection) {
        l.info("A#setBCollection( size: "+bCollection.size()+" )");
        this.bCollection = bCollection;
    }

    public A() {
        l.info("A()");
    }

    public A(Integer id, String a1) {
        l.info("A("+id+","+a1+")");
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
        return A.class.getName()+"[ id = " + id + ", a1 = "+a1+" * "+getBCollection().size()+" ]";
    }

    
}
