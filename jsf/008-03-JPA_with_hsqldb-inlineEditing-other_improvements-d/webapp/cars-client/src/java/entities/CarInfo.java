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
    @NamedQuery(name = "CarInfo.findAll"        , query = "SELECT x FROM CarInfo x"),
    @NamedQuery(name = "CarInfo.findByModelSpec", query = "SELECT x FROM CarInfo x WHERE x.modelSpec = :MODELSPEC")
        }) 
public class CarInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name="model", column=@Column(name="MODEL")),
                @AttributeOverride(name="spec" , column=@Column(name="SPEC"))
                })
    private ModelSpec modelSpec;

    /*
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MODEL")
    private String model;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPEC")
    private String spec;
    */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPEC_VALUE")
    private String specValue;

    @JoinColumn(name = "model", referencedColumnName = "model")
    @ManyToOne(optional = false)
    private Car car;



    public CarInfo() {}

    public CarInfo(ModelSpec modelSpec, String specValue) {
        this.modelSpec = modelSpec;
        this.specValue = specValue;
    }

    public ModelSpec getModelSpec() {
        return modelSpec;
    }

    public void setModelSpec(ModelSpec modelSpec) {
        this.modelSpec = modelSpec;
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
        hash +=     (modelSpec     != null ? modelSpec .hashCode() : 0);
        hash += 173*(specValue     != null ? specValue .hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CarInfo)) {
            return false;
        }
        CarInfo other = (CarInfo) object;
        if ((this.modelSpec == null && other.modelSpec != null) || 
            (this.modelSpec != null && !this.modelSpec.equals(other.modelSpec))) {
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
        return CarInfo.class.getName()+"[ modelSpec =" + modelSpec+", specValue = "+specValue+" ]";
    }
}
