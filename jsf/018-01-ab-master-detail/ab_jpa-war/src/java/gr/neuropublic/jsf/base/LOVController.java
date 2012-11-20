package gr.neuropublic.jsf.base;

import java.util.List;
import java.util.Map;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Abstract Controller Class used to provide common operations LOV data collections
 * 
 * @author g_dimitriadis
 */
public abstract class LOVController<T> extends PaginableController<T> {
    
    private static Logger logger = LoggerFactory.getLogger(LOVController.class);
    
    /**
     * Implements AJAX row selection
     * 
     * It is required for LOV Page selection buttons and other AJAX components 
     * (e.g. p:contextMenu) for selection to work properly
     *  
     * @param event 
     */
    public void onRowSelect(SelectEvent event) {
        current = (T)event.getObject();
        logger.debug("onRowSelect: s"+current);
    }
    
    
    private LovHelper<T> lovHelper;
    public LovHelper<T> getLovHelper() {
        return lovHelper;
    }
    public void setLovHelper(LovHelper<T> lovHelper) {
        this.lovHelper = lovHelper;
    }
    
    
    public void returnFromLOV() {
        getLovHelper().saveEnity(current);
    }
    
    protected List filterData(Map filters, int[] range, int[] rowCount, String sortField, boolean sortOrder) {
        LovHelper<T> lv = getLovHelper();
        Map tmp = lv.getFilters();
        
        filters.putAll(tmp);
        return super.filterData(filters, range, rowCount, sortField, sortOrder);
    }
    
    
}