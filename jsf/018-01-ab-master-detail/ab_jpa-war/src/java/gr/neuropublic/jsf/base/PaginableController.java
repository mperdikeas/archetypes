package gr.neuropublic.jsf.base;

import gr.neuropublic.base.IFacade;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Controller Class used to provide common operations for pagination of data collections
 * 
 * Front End Implementation is PrimeFaces
 * 
 * @author g_dimitriadis
 */
public abstract class PaginableController<T> extends EntityBasedController<T> {
    
    private static Logger logger = LoggerFactory.getLogger(PaginableController.class);
  
    protected LazyDataModel items = null;
    
    
    /**
     * Creates page data model using PrimeFaces lazy loading pagination mechanism
     * 
     * @return 
     */
    public LazyDataModel createPageDataModel() {
        LazyDataModel model = new LazyDataModel() {

            @Override
            public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                logger.debug("Load Data to UI... filters: " + filters + " - pageSize: " + pageSize + " - first: " + first + " - sortField: " + sortField);
                System.out.println("Load Data to UI... filters: " + filters + " - pageSize: " + pageSize + " - first: " + first + " - sortField: " + sortField);
                int[] estimatedRowCount = new int[1];  
                boolean ascendingOrder = (sortOrder.equals(SortOrder.DESCENDING))?false:true;
                List<T> paginatorList = filterData(filters, new int[]{first, first + pageSize}, estimatedRowCount, sortField, ascendingOrder);
                
                this.setRowCount(estimatedRowCount[0]);
                return paginatorList;
            }
        };
        
        return model;
    }
    
    /**
     * Gets model
     * 
     * @return 
     */
    public LazyDataModel getItems() {
        logger.debug("getItems() "+getFacade().getClass());
        if (items == null) {
            items = createPageDataModel();
        }
        return items;
    }
    
    /**
     * This method must be overridden in order to add filtering parameters or call a different facade method (e.g. when using cascaded lists)
     * 
     * @param filters
     * @param range
     * @param rowCount
     * @param sortField
     * @param sortOrder
     * @return 
     */
    protected List filterData(Map filters, int[] range, int[] rowCount, String sortField, boolean sortOrder) {
        
        return getFacade().findAllByCriteriaRange(filters, range, rowCount, sortField, sortOrder);
    }
    

    // ***** ABSTRACT HOOKS ******
    
    /**
     * This getter is used to return the injected facade class
     * 
     * @return 
     */
    protected abstract IFacade getFacade();
    
    public void recreateModel() {
        //tManager.refreshTransactionStatus();
        items = null;
    }
    
}