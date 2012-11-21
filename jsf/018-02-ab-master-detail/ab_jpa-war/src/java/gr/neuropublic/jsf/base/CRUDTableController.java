package gr.neuropublic.jsf.base;

import gr.neuropublic.jsf.util.JsfUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author g_dimitriadis
 */
public abstract class CRUDTableController<T> extends PaginableController<T> {

    private static Logger l =
            LoggerFactory.getLogger(CRUDTableController.class);
    private TransactionManager tManager;
	
	protected TransactionManager getTransactionManager() {
		return tManager;
	}

    public CRUDTableController() {
        tManager = (TransactionManager) JsfUtil.accessBeanFromFacesContext("transactionManager");
    }

    /**
     * Creates page data model using PrimeFaces lazy loading pagination
     * mechanism
     *     
* @return
     */
    @Override
    public LazyDataModel createPageDataModel() {

        LazyDataModel model = new LazyDataModel() {
            @Override
            public List load(int first, int pageSize, String sortField,
                    SortOrder sortOrder, Map filters) {
                l.info("Load Data to UI... filters: " + filters
                        + " - pageSize: " + pageSize + " - first: " + first + " - sortField: "
                        + sortField);

                int[] estimatedRowCount = {0};

                boolean ascendingOrder =
                        (sortOrder.equals(SortOrder.DESCENDING)) ? false : true;
                
                List dbData = filterData(filters, new int[]{first, first
                            + pageSize}, estimatedRowCount, sortField,
                        ascendingOrder);
                
                List finalData = tManager.applyPendingChanges(dbData, estimatedRowCount);
                
                this.setRowCount(estimatedRowCount[0]);
                
                if (!finalData.isEmpty())
                    setCurrent((T)finalData.get(0));
                else
                    setCurrent(null);

                return finalData;
            }
        };

        return model;
    }

    @Override
    public void recreateModel() {
        tManager.refreshTransactionStatus();
        //items = null;
    }

    /**
     * Add an empty row
     */
    public void addRow() {
        l.info("add Row");
        current = (T)getFacade().initRow();
        tManager.create(current);
        recreateModel();
    }

    /**
     * Delete entity from session
     *     
* @param entity
     */
    public void delete(T entity) {
        tManager.delete(entity);
        current = null;
        recreateModel();
    }
    
    public void edit(T entity){
        tManager.edit(entity);
    }

    public void edit(ValueChangeEvent event) {
        l.info("field value has changed to: " + event.getNewValue());
        T entity = (T) JsfUtil.getRowData(event.getComponent());
        edit(entity);
    }
    
    
    
    public void commit(ActionEvent actionEvent)
    {
        this.commit();
    }

    /**
     * Commit all CUD changes to database
     */
    public void commit()
    {
        l.info("commit");

        //In this case, changes are commited one by one
        //In a real time application changes should be commited only once at the end of the transaction and... 
        //if an error occurs all changes should be rollbacked

        //commitOneByOne();
        //commitOnce()  TODO implement
        
        //Commit new,updated,deleted rows at once
        List<T> rowsForInsert = (List<T>) tManager.getRowsForInsert();
        List<T> rowsForDelete = (List<T>) tManager.getRowsForDelete();
        List<T> rowsForUpdate = (List<T>) tManager.getRowsForUpdate();
        
        getFacade().synchronizeChangesWithDb(rowsForInsert, rowsForDelete, rowsForUpdate);
        
        //Empty Lists after succesful synchronization with db
        tManager.getRowsForInsert().clear();
        tManager.getRowsForDelete().clear();
        tManager.getRowsForUpdate().clear();
        
        
        if (!tManager.isTransactionDirty()) 
        {
            JsfUtil.addSuccessMessage(JsfUtil.getMsgString("UncommittedChangesMsg"));
        } 
        else 
        {
            tManager.refreshTransactionStatus();
        }
    }
    
//    private void commitOneByOne() {
//      
//        commitNewRows();
//        commitDeletedRows();
//        commitUpdatedRows();
//    }

//    private void commitNewRows() {
//
//        List<T> rowsForInsert = (List<T>) tManager.getRowsForInsert();
//        if (!rowsForInsert.isEmpty()) {
//            Iterator<T> iter = rowsForInsert.iterator();
//            while (iter.hasNext()) {
//                T item = iter.next();
//                try {
//                    getFacade().merge(item);
//                    //getFacade().persist(item); //persist does not work because id is repopulated
//                    l.info("insert item to db =" + item);
//                    JsfUtil.addSuccessMessage(JsfUtil.getMsgString("EntityCreationMsg", new Object[]{item}));
//                    iter.remove();
//                }
//                catch(Exception e) {
//                    l.error("Error creating item: ", e);
//                    JsfUtil.addErrorMessage(JsfUtil.getMsgString("EntityCreationError", new Object[]{item}), e.getMessage());
//                    markRowForError(item);
//                }
//            }
//        }
//    }
    

    
//    private void commitDeletedRows() {
//
//        List<T> rowsToDelete = (List<T>) tManager.getRowsForDelete();
//        if (!rowsToDelete.isEmpty()) {
//            Iterator<T> iter = rowsToDelete.iterator();
//            while (iter.hasNext()) {
//                T item = iter.next();
//                try {
//                    getFacade().remove(item);
//                    l.info("delete item from db =" + item);
//                    JsfUtil.addSuccessMessage(JsfUtil.getMsgString("EntityRemovalMsg", new Object[]{item}));
//                    iter.remove();
//                }
//                catch(Exception e) {
//                    l.error("Error deleting item: ", e);
//                    JsfUtil.addErrorMessage(JsfUtil.getMsgString("EntityRemovalError", new Object[]{item}), e.getMessage());
//                    markRowForError(item);
//                }
//                
//            }
//        }
//    }

//    private void commitUpdatedRows() {
//        List<T> rowsForUpdate = (List<T>) tManager.getRowsForUpdate();
//        if (!rowsForUpdate.isEmpty()) {
//            Iterator<T> iter = rowsForUpdate.iterator();
//            while (iter.hasNext()) {
//                T item = iter.next();
//                try {
//                    getFacade().merge(item);
//                    l.info("update item to db =" + item);
//                    JsfUtil.addSuccessMessage(JsfUtil.getMsgString("EntityUpdateMsg", new Object[]{item}));
//                    iter.remove();
//                }
//                catch(Exception e) {
//                    l.error("Error updating item: ", e);
//                    JsfUtil.addErrorMessage(JsfUtil.getMsgString("EntityUpdateError", new Object[]{item}), e.getMessage());
//                    markRowForError(item);
//                }
//            }
//        }
//    }
    
//    private void markRowForError(T item) {
//        List<T> allRows = (List<T>)items.getWrappedData();
//        int index = -1;
//        for (int i = 0; i < allRows.size(); i++) {
//            if (allRows.get(i).equals(item)) {
//                index = i;
//                break;
//            }
//        }
//        if (index != -1) {
//            RequestContext requestContext = RequestContext.getCurrentInstance();
//            requestContext.execute("markRowForError("+index+");");
//        }
//    }

//    public void revertChanges() {
//        tManager.clearDirtyData();
//        items = null;
//    }
    

}