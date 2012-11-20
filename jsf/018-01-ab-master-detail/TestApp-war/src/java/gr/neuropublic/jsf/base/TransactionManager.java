package gr.neuropublic.jsf.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for caching and managing table rows eligible to be
 * persisted and tracking the status of these rows (transactionDirty)
 *
 * @author giorgos
 */
@ManagedBean(name = "transactionManager")
@SessionScoped
public class TransactionManager implements Serializable {

    private static Logger logger =
            LoggerFactory.getLogger(TransactionManager.class);
    private List rowsForUpdate = new ArrayList();
    private List rowsForDelete = new ArrayList();
    private List rowsForInsert = new ArrayList();
    private boolean transactionDirty = false;

    public boolean isTransactionDirty() {
        return transactionDirty;
    }

    public void setTransactionDirty(boolean transactionDirty) {
        this.transactionDirty = transactionDirty;
    }

    public boolean refreshTransactionStatus() {
        transactionDirty = (!rowsForUpdate.isEmpty()
                || !rowsForDelete.isEmpty()
                || !rowsForInsert.isEmpty());
        return transactionDirty;
    }

    public void delete(Object entity) {
        if (rowsForInsert.contains(entity)) {
            rowsForInsert.remove(entity);
        } else {
            rowsForDelete.add(entity);
            if (rowsForUpdate.contains(entity)) {
                rowsForUpdate.remove(entity);
            }
        }
    }

    public void edit(Object entity) {
        if (!rowsForUpdate.contains(entity)
                && !rowsForInsert.contains(entity)) {
            rowsForUpdate.add(entity);
        }

        refreshTransactionStatus();
    }

    public void create(Object entity) {
        rowsForInsert.add(entity);
        
    }

    public void clearDirtyData() {
        rowsForUpdate.clear();
        rowsForInsert.clear();
        rowsForDelete.clear();
        refreshTransactionStatus();
    }
    
    public List applyPendingChanges(List dbData) {
        return applyPendingChanges(dbData, null);
    }

    public List applyPendingChanges(List dbData, int[] estimatedRowCount) {
        List finalData = new ArrayList();
        populateInsertedRows(finalData, estimatedRowCount);
        finalData.addAll(dbData);
        populateUpdatedRows(finalData);
        populateDeletedRows(finalData, estimatedRowCount);
        return finalData;
    }

    private void populateInsertedRows(List data) {
        populateInsertedRows(data, null);
    }

    private void populateInsertedRows(List data, int[] estimatedRowCount) {
        if (transactionDirty) {
            for (Object item : rowsForInsert) {
                data.add(item);
                if (estimatedRowCount != null) {
                    estimatedRowCount[0] += 1;
                }
            }
        }
    }

    private void populateDeletedRows(List data) {
        populateDeletedRows(data, null);
    }

    private void populateDeletedRows(List data, int[] estimatedRowCount) {
        if (transactionDirty) {
            for (Object item : rowsForDelete) {
                logger.info("delete from datamodel " + item);
                data.remove(item);
                if (estimatedRowCount != null) {
                    estimatedRowCount[0] -= 1;
                }
            }
        }
    }

    private void populateUpdatedRows(List data) {
        if (transactionDirty && !rowsForUpdate.isEmpty()) {
            for (Object item : rowsForUpdate) {
                if (data.contains(item)) {
                    for (int i = 0; i < data.size(); i++) {
                        if (item.equals(data.get(i))) {
                            data.set(i, item);
                            break;
                        }
                    }
                }
            }
        }
    }

    public List getRowsForDelete() {
        return rowsForDelete;
    }

    public void setRowsForDelete(List rowsForDelete) {
        this.rowsForDelete = rowsForDelete;
    }

    public List getRowsForInsert() {
        return rowsForInsert;
    }

    public void setRowsForInsert(List rowsForInsert) {
        this.rowsForInsert = rowsForInsert;
    }

    public List getRowsForUpdate() {
        return rowsForUpdate;
    }

    public void setRowsForUpdate(List rowsForUpdate) {
        this.rowsForUpdate = rowsForUpdate;
    }
}
