/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.neuropublic.jsf.base;

import gr.neuropublic.neurojsfpilot.jsfutil.JsfUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author g_dimitriadis
 */
public abstract class CRUDTableController<T> extends PaginableController {

    private static Logger logger =
            LoggerFactory.getLogger(CRUDTableController.class);
    private List<T> rowsForUpdate = new ArrayList<T>();
    private List<T> rowsForDelete = new ArrayList<T>();
    private List<T> rowsForInsert = new ArrayList<T>();
    private boolean transactionDirty = false;
    
    private int firstRowIndex;

    public int getFirstRowIndex() {
        return firstRowIndex;
    }

    public void setFirstRowIndex(int firstRowIndex) {
        this.firstRowIndex = firstRowIndex;
    }

    public boolean isTransactionDirty() {
        return transactionDirty;
    }

    public void setTransactionDirty(boolean transactionDirty) {
        this.transactionDirty = transactionDirty;
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
                logger.info("Load Data to UI... filters: " + filters
                        + " - pageSize: " + pageSize + " - first: " + first + " - sortField: "
                        + sortField);

                int[] estimatedRowCount = {0};
                firstRowIndex = first;

                List paginatorList = new ArrayList();
                if (transactionDirty) {
                    for (T item : rowsForInsert) {
                        logger.info("insert to datamodel " + item);
                        paginatorList.add(item); //TODO add all new rows here
                        estimatedRowCount[0] += 1;
                    }
                }

                boolean ascendingOrder =
                        (sortOrder.equals(SortOrder.DESCENDING)) ? false : true;
                paginatorList.addAll(filterData(filters, new int[]{first, first + pageSize}, estimatedRowCount, sortField,
                        ascendingOrder));

                if (transactionDirty) {
                    updatePageDataModel(paginatorList, estimatedRowCount);
                }
                this.setRowCount(estimatedRowCount[0]);

                return paginatorList;
            }
        };

        return model;
    }

    private void updatePageDataModel(List paginatorList, int[] rowCount) {


        for (T item : rowsForDelete) {
            logger.info("delete from datamodel " + item);
            paginatorList.remove(item);
            rowCount[0] -= 1;
        }
    }

    public void recreateModel() {
        checkPendingChanges();
        items = null;
    }

    /**
     * Add an empty row
     */
    public void addRow() {
        logger.info("addRow ");
        current = getFacade().createRow();
        rowsForInsert.add((T) current);
        recreateModel();
    }

    /**
     * Delete entity from session
     *
     * @param entity
     */
    public void delete(T entity) {

        logger.info("delete " + entity);
        if (rowsForInsert.contains(entity)) {
            rowsForInsert.remove(entity);
        } else {
            rowsForDelete.add(entity);
            if (rowsForUpdate.contains(entity)) {
                rowsForUpdate.remove(entity);
            }
        }
        current = null;
        recreateModel();
    }

    private boolean checkPendingChanges() {
        transactionDirty = (!rowsForUpdate.isEmpty()
                || !rowsForDelete.isEmpty()
                || !rowsForInsert.isEmpty());
        return transactionDirty;
    }

    public void edit(ValueChangeEvent event) {
        logger.info("field value has changed to: " + event.getNewValue());

        T entity = (T) JsfUtil.getRowData(event.getComponent());

        if (!rowsForUpdate.contains(entity)
                && !rowsForInsert.contains(entity)) {
            rowsForUpdate.add(entity);
        }

        checkPendingChanges();
    }

    /**
     * Save all CRUD changes to database
     */
    public void save() {
        logger.info("save");
//boolean saved = (saveNewRows() || saveDeletedRows() || saveUpdatedRows());
        saveNewRows();
        saveDeletedRows();
        saveUpdatedRows();

        if (!transactionDirty) {
            JsfUtil.addErrorMessage("No changes to commit");
        } else {
            recreateModel();
        }
    }

    private boolean saveNewRows() {
        boolean saved = false;

        if (!rowsForInsert.isEmpty()) {
            saved = true;
            for (T item : rowsForInsert) {
                logger.info("insert item to db =" + item);
                getFacade().create(item);
                JsfUtil.addSuccessMessage("Entity Created " + item);
//rowsForInsert.remove(item); //TODO remove items from list one by one
            }
            rowsForInsert.clear();
        }

        return saved;
    }

    private boolean saveDeletedRows() {
        boolean saved = false;

        if (!rowsForDelete.isEmpty()) {
            saved = true;
            for (T item : rowsForDelete) {
                logger.info("delete item from db =" + item);
                getFacade().remove(item);
                JsfUtil.addSuccessMessage("Entity Removed " + item);
//rowsForInsert.remove(item); //TODO remove items from list one by one
            }
            rowsForDelete.clear();
        }
        return saved;
    }

    private boolean saveUpdatedRows() {
        boolean saved = false;

        if (!rowsForUpdate.isEmpty()) {
            saved = true;
            for (T item : rowsForUpdate) {
                logger.info("update item to db =" + item);
                getFacade().edit(item);
                JsfUtil.addSuccessMessage("Entity updated " + item);
//rowsForUpdate.remove(item); //TODO remove items from list one by one
            }
            rowsForUpdate.clear();
        }
        return saved;
    }
}