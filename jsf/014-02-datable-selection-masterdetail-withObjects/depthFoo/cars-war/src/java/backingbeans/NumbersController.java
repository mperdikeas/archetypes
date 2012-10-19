package backingbeans;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.bean.ManagedProperty;
import java.util.logging.Logger;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import java.util.Map;
import java.util.HashMap;
import javax.faces.event.ComponentSystemEvent;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

import facades.ICarFacadeLocal;
import entities.Car;

@ManagedBean
@ViewScoped // if this is toggled to @RequestScoped it stops working
//@RequestScoped
public class NumbersController implements Serializable {


    private static final Logger l = Logger.getLogger(NumbersController.class.getName());

    public List<DivisorSet> getDivisorSets() { 
        List<DivisorSet> retValue = new ArrayList<DivisorSet>();
        for (int i = 10 ; i < 20 ; i++)
            retValue.add( new DivisorSet(i) );
        l.info(String.format("getDivisorSets() returning %d divisor sets", retValue.size()));
        return retValue;
    }

    public List<Divisor> getDivisors() { 
        l.info("getDivisors()");
        if (selectedDivisorSet != null) {
                List<Integer> divisorsIL  = selectedDivisorSet.getDivisors();
                List<Divisor> retValue = new ArrayList<Divisor>();
                for (int i : divisorsIL)
                    retValue.add(new Divisor(i));
                l.info(String.format("getDivisors() returning %d divisors", retValue.size()));
                return retValue;
            }
        else {
            l.info("getDivisors() returning null");
            return null;
        }
    }

    private DivisorSet    selectedDivisorSet;
    public  DivisorSet getSelectedDivisorSet() {
        l.info("getSelectedDivisorSet() returning: "+selectedDivisorSet);
        return    selectedDivisorSet;
    }
    public void        setSelectedDivisorSet   (DivisorSet selectedDivisorSet) {
        l.info(       "setSelectedDivisorSet("        +    selectedDivisorSet+")");
        this.             selectedDivisorSet          =    selectedDivisorSet;
    }

    private Divisor   selectedDivisor;
    public Divisor getSelectedDivisor() {
        l.info("getSelectedDivisor() returning: "+selectedDivisor);
        return selectedDivisor;
    }
    public void    setSelectedDivisor   (Divisor selectedDivisor) {
        l.info(   "setSelectedDivisor("    +     selectedDivisor+")");
        this.      selectedDivisor         =     selectedDivisor;       
    }

    public String foo() { return null; }
}

