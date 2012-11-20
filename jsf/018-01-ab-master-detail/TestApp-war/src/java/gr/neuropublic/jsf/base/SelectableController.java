package gr.neuropublic.jsf.base;

import java.util.List;
import javax.faces.model.SelectItem;

/**
 * Abstract controller class used for selectMany and selectOne components
 *
 * @author g_dimitriadis
 */
public abstract class SelectableController<T> extends PaginableController<T> {

    /**
     * Method used to select one item from a list CAUTION: Must be avoided for
     * large Data Sets
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return getSelectItems(filterData());
    }
    
    /**
     * You need override this method if you need to filter target data collection
     * 
     * @return 
     */
    protected List filterData() {
        return getFacade().findAll(); //by default no data is filtered
    }

    protected abstract String getLabel(T entity);

    private SelectItem[] getSelectItems(List<T> entities) {
        int size = entities.size() + 1;
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        
        items[0] = new SelectItem("", "---");
        i++;

        for (T x : entities) {
            items[i++] = new SelectItem(x, getLabel(x));
        }
        return items;
    }
}