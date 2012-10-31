package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.logging.Logger;

@Entity
@Table(name = "B")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "B.findAll"  , query = "SELECT x FROM B x"),
    @NamedQuery(name = "B.findById" , query = "SELECT x FROM B x WHERE x.id = :ID")
        }) 
public class B implements Serializable {
    
    private static final Logger l = Logger.getLogger(B.class.getName());
    private static final long serialVersionUID = 1L;
    

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)   
    @Column(name = "ID")
    private Integer id;


    @Basic(optional = false)
    @NotNull
    @Column(name="B1")
    private String b1;


    @JoinColumn(name = "A_ID", referencedColumnName = "ID", nullable=false) // line 39
    @ManyToOne(optional = false)
    // @NotNull // line 41
    private A aId;


    public B() {
        l.info("B()");
    }

    public B(Integer id, String b1) {
        this.id = id;
        this.b1 = b1;
    }

    
    public Integer getIdentityHashCode() {
        return System.identityHashCode(this);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public void setAId(A aId) {
        if (this.aId != null) { this.aId.internalRemoveB(this); }
        this.aId = aId;
        if (aId !=null) { aId.internalAddB(this); }
    }

    public A getAId() { return aId; }

    public void nullifyAId() {
        this.aId = null;
    }

    public String getB1() { return b1; }
    public void setB1(String b1) { this.b1 = b1; }
    

    /* I took the pattern from here: http://blog.xebia.com/2009/04/09/jpa-implementation-patterns-removing-entities/
       but as I discovered in archetypes#JPA#001#06 it produces a 'concurrent modification exception' so I am removing
       the hook.

       @PreRemove
    public void preRemove() {
        l.info("inside preRemove for b="+this);
        setAId(null);
        } */ // line 54

    public void preRemoveCutFromA() {
        l.info("inside preRemoveCutFromA for b="+this);
        if (aId !=null) aId.internalRemoveB(this);
        //nullifyAId();
    }


    

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof B)) {
            return false;
        }
        B other = (B) object;
        if ((this.id == null && other.id != null) || 
            (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.aId == null && other.aId != null) || 
            (this.aId != null && !this.aId.equals(other.aId))) {
            return false;
        } 
        if ((this.b1 == null && other.b1 != null) || 
            (this.b1 != null && !this.b1.equals(other.b1))) {
            return false;
        } 
        return true;
    }

    @Override
    public String toString() {
        return B.class.getName()+"[ id =" + id+", aId = "+aId+", b1="+b1+"]";
    }
}
