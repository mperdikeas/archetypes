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
public class NumbersController implements Serializable {


    private static final Logger l = Logger.getLogger(NumbersController.class.getName());

    public List<DivisorSet> getDivisorSets() { 
        List<DivisorSet> retValue = new ArrayList<DivisorSet>();
        for (int i = 10 ; i < 20 ; i++)
            retValue.add( new DivisorSet(i) );
        return retValue;
    }

    public List<Integer> getDivisors() { 
        if (selectedDivisorSet != null)
            return selectedDivisorSet.getDivisors();
        else return null;
    }

    private DivisorSet    selectedDivisorSet;
    public  DivisorSet getSelectedDivisorSet() { return    selectedDivisorSet; }
    public void        setSelectedDivisorSet   (DivisorSet selectedDivisorSet) {
        l.info(       "setSelectedDivisorSet("        +    selectedDivisorSet+")");   
        this.             selectedDivisorSet          =    selectedDivisorSet;
    }

    private Integer   selectedDivisor;
    public Integer getSelectedDivisor() { return selectedDivisor; }
    public void    setSelectedDivisor   (Integer selectedDivisor) {
        l.info(   "setSelectedDivisor("    +     selectedDivisor+")");
        this.      selectedDivisor         =     selectedDivisor;       
    }

    public String foo() { return null; }
}

