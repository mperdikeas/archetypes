package entities;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "B")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "B.findAll"  , query = "SELECT x FROM B x"),
    @NamedQuery(name = "B.findById" , query = "SELECT x FROM B x WHERE x.id = :ID")
        }) 
public class B implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull    
    @Column(name = "ID")
    private Integer id;


    @Basic(optional = false)
    @NotNull
    @Column(name="B1")
    private String b1;


    @JoinColumn(name = "A_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private A aId;


    public B() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }


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
