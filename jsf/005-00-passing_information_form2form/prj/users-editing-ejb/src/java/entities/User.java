package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "public.user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT x FROM User x"),
    @NamedQuery(name = "User.findByUser_Name", query = "SELECT x FROM User x WHERE x.id = :id")
}) 
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    public void setId(Integer id) {this.id = id;}
    public Integer getId() {return this.id;}

    @Basic(optional = false)
    @NotNull
    private String firstname;
    public void setFirstname(String firstname) {this.firstname = firstname;}
    public String getFirstname() {return this.firstname;}

    @Basic(optional = false)
    @NotNull
    private String surname;
    public void setSurname(String surname) {this.surname = surname;}
    public String getSurname() {return this.surname;}

    @Basic(optional = false)
    @NotNull
    private Integer age;
    public void setAge(Integer age) {this.age = age;}
    public Integer getAge()  {return this.age;}

    public User() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || 
            (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return User.class.getName()+"[ id =" + id + ", firstname = "+firstname+ ", surname = "+surname+", age = "+age+" ]";
    }
    
}
