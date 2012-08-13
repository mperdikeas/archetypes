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
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT x FROM User x"),
    @NamedQuery(name = "User.findByUser_Name", query = "SELECT x FROM User x WHERE x.userName = :userName")
        }) 
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "jdbccoodrs") // sic, type in the DB
    private String jdbcCoords;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String jdbcCoords) {
        this.userName = userName;
        this.jdbcCoords = jdbcCoords;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJdbcCoords() {
        return jdbcCoords;
    }

    public void setJdbcCoords(String jdbcCoords) {
        this.jdbcCoords = jdbcCoords;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userName != null ? userName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userName == null && other.userName != null) || 
            (this.userName != null && !this.userName.equals(other.userName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return User.class.getName()+"[ userName=" + userName + ", jdbcCoords="+jdbcCoords+" ]";
    }
    
}
