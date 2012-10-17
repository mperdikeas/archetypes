package entities;

import java.util.logging.Logger;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "CAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Car.findAll"    , query = "SELECT x FROM Car x"),
    @NamedQuery(name = "Car.findByModel", query = "SELECT x FROM Car x WHERE x.model = :MODEL")
        }) 
public class Car implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private static final Logger l = Logger.getLogger(Car.class.getName());

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MODEL")
    private String model;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private Integer price;

    public String stringifyCollection() {
        StringBuffer sb = new StringBuffer();
        for (CarInfo carInfo : carInfoCollection)
            sb.append(carInfo.toString());
        return sb.toString();
    }


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modelSpec.model", fetch=FetchType.EAGER)
    private Collection<CarInfo> carInfoCollection;

    public Collection<CarInfo> getCarInfoCollection() { 
        l.info("^^^^^^^^^^^^^^^^ getCarInfoCollection called on: "+System.identityHashCode(this));
        l.info("^^^^^^^^^^^^^^^^ returning: "+stringifyCollection());
        return carInfoCollection;
    }
    public void setCarInfoCollection(Collection<CarInfo> carInfoCollection) {
        l.info("! ! ! ! ! ! ! ! ! setCarInfoCollection called on: "+System.identityHashCode(this));
        this.carInfoCollection = carInfoCollection;
    }

    public Car() {}

    public Car(String model, Integer price) {
        this.model = model;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public int getHashCode() {
        return hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (model != null ? model.hashCode() : 0);
        hash += (price != null ? price.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.model == null && other.model != null) || 
            (this.model != null && !this.model.equals(other.model))) {
            return false;
        }
        if ((this.price == null && other.price != null) || 
            (this.price != null && !this.price.equals(other.price))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Car.class.getName()+"[ model =" + model + ", price ="+price+", features: "+stringifyCollection()+" ]";
    }
    
}
