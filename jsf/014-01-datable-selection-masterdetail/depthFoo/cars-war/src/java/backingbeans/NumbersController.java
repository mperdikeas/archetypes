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
@RequestScoped
public class NumbersController implements Serializable {


    private static final Logger l = Logger.getLogger(NumbersController.class.getName());

    public List<Integer> getEvenNumbers() { 
        List<Integer> retValue = new ArrayList<Integer>();
        for (int i = 0 ; i < 10 ; i++)
            if (i % 2 == 0)
                retValue.add(i);
        return retValue;
    }

    public List<Integer> getOddNumbers() { 
        List<Integer> retValue = new ArrayList<Integer>();
        for (int i = 0 ; i < 10 ; i++)
            if (i % 2 != 0)
                retValue.add(i);
        return retValue;
    }

    private Integer   selectedEven;
    public Integer getSelectedEven() { return selectedEven; }
    public void    setSelectedEven   (Integer selectedEven) {
        l.info(   "setSelectedEven("     +    selectedEven+")");   
        this.      selectedEven          =    selectedEven;
    }

    private Integer   selectedOdd;
    public Integer getSelectedOdd() { return selectedOdd; }
    public void    setSelectedOdd   (Integer selectedOdd) {
        l.info(   "setSelectedOdd("    +     selectedOdd+")");   // no, it turns out I FAILED to replicate it at commit b12557b - it was
        this.      selectedOdd         =     selectedOdd;        // just a silly typo-bug
    }

    public String foo() { return null; }
}

