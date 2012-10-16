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
@Table(name = "CAR_INFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarInfo.findAll"    , query = "SELECT x FROM CarInfo x"),
    @NamedQuery(name = "CarInfo.findByModel", query = "SELECT x FROM CarInfo x WHERE x.model = :MODEL")
        }) 
public class CarInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MODEL")
    private String model;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPEC")
    private String spec;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPEC_VALUE")
    private String specValue;

    @JoinColumn(name = "model", referencedColumnName = "model")
    @ManyToOne(optional = false)
    private Car car;



    public CarInfo() {}

    public CarInfo(String model, String spec, String specValue) {
        this.model     = model;
        this.spec      = spec;
        this.specValue = specValue;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }
    
    public int getHashCode() {
        return hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash +=     (model     != null ? model    .hashCode() : 0);
        hash += 31 *(spec      != null ? spec     .hashCode() : 0);
        hash += 173*(specValue != null ? specValue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CarInfo)) {
            return false;
        }
        CarInfo other = (CarInfo) object;
        if ((this.model == null && other.model != null) || 
            (this.model != null && !this.model.equals(other.model))) {
            return false;
        }
        if ((this.spec == null && other.spec != null) || 
            (this.spec != null && !this.spec.equals(other.spec))) {
            return false;
        } 
        if ((this.specValue == null && other.specValue != null) || 
            (this.specValue != null && !this.specValue.equals(other.specValue))) {
            return false;
        } 
        return true;
    }

    @Override
    public String toString() {
        return CarInfo.class.getName()+"[ model =" + model + ", spec ="+spec+", specValue = "+specValue+" ]";
    }
    
}
