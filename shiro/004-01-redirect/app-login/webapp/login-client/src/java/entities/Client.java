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
@Table(name = "CLIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll"    , query = "SELECT x FROM Client x"),
    @NamedQuery(name = "Client.findByModel", query = "SELECT x FROM Client x WHERE x.clientName = :CLIENTNAME")
        }) 
public class Client implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLIENTNAME")
    private String clientName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COORDS")
    private String coords;

    public Client() {
    }

    public Client(String clientName, String coords) {
        this.clientName = clientName;
        this.coords = coords;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }
    
    public int getHashCode() {
        return hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash +=      (clientName != null ? clientName.hashCode() : 0);
        hash += 3701*(coords     != null ? coords.hashCode()     : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.clientName == null &&                     other.clientName != null) || 
            (this.clientName != null && !this.coords.equals(other.clientName))) {
            return false;
        }
        if ((this.coords == null && other.coords != null) || 
            (this.coords != null && !this.coords.equals(other.coords))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Client.class.getName()+"[ clientName =" + clientName + ", coords ="+coords+" ]";
    }
    
}
