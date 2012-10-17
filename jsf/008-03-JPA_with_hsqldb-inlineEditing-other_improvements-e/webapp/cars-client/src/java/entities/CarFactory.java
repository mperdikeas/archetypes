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
    @NamedQuery(name = "CarFactory.findAll"        , query = "SELECT x FROM CarFactory x"),
    @NamedQuery(name = "CarFactory.findByModelSpec", query = "SELECT x FROM CarFactory x WHERE x.modelSpec = :MODELSPEC")
        }) 
public class CarFactory implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name="model", column=@Column(name="MODEL")),
                @AttributeOverride(name="spec" , column=@Column(name="SPEC"))
                })
    private ModelFactory modelFactory;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FACTORY_DESC")
    private String factoryDesc;




    public CarFactory() {}

    public CarFactory(ModelFactory modelFactory, String factoryDesc) {
        this.modelFactory = modelFactory;
        this.factoryDesc = factoryDesc;
    }

    public ModelFactory getModelFactory() {
        return modelFactory;
    }

    public void setModelFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    public String getFactoryDesc() {
        return factoryDesc;
    }

    public void setFactoryDesc(String factoryDesc) {
        this.factoryDesc = factoryDesc;
    }
    
    public int getHashCode() {
        return hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CarFactory)) {
            return false;
        }
        CarFactory other = (CarFactory) object;
        if ((this.modelFactory == null && other.modelFactory != null) || 
            (this.modelFactory != null && !this.modelFactory.equals(other.modelFactory))) {
            return false;
        }
        if ((this.factoryDesc == null && other.factoryDesc != null) || 
            (this.factoryDesc != null && !this.factoryDesc.equals(other.factoryDesc))) {
            return false;
        } 
        return true;
    }

    @Override
    public String toString() {
        return CarFactory.class.getName()+"[ modelFac =" + modelFactory+", factoryDesc = "+factoryDesc+" ]";
    }
}
