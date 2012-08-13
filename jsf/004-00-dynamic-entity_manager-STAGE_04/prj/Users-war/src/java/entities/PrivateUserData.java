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
@Table(name = "privateuserdata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrivateUserData.findAll"       , query = "SELECT x FROM PrivateUserData x"),
    @NamedQuery(name = "PrivateUserData.findByEmployee", query = "SELECT x FROM PrivateUserData x WHERE x.employee = :employee")
             })
public class PrivateUserData implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "employee")
    private String employee;
    @Basic(optional = false)
    @NotNull
    @Column(name = "employee_personal")
    private String employeePersonal;

    public PrivateUserData() {
    }

    public PrivateUserData(String employee) {
        this.employee = employee;
    }

    public PrivateUserData(String employee, String employeePersonal) {
        this.employee = employee;
        this.employeePersonal = employeePersonal;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getEmployeePersonal() {
        return employeePersonal;
    }

    public void setEmployeePersonal(String employeePersonal) {
        this.employeePersonal = employeePersonal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employee != null ? employee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PrivateUserData)) {
            return false;
        }
        PrivateUserData other = (PrivateUserData) object;
        if ((this.employeePersonal == null && other.employeePersonal != null) || 
            (this.employeePersonal != null && !this.employeePersonal.equals(other.employeePersonal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return PrivateUserData.class.getName()+"[ employee =" + employee + ", employeePersonal ="+employeePersonal+" ]";
    }
    
}
