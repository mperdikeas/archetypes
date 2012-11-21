package gr.neuropublic.jsf.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class EntityBasedController<T> {
    
    private static Logger logger = LoggerFactory.getLogger(PaginableController.class);
    
    protected T current;
    
    public T getCurrent() {
        return current;
    }
    
    public void setCurrent(T current) {
	//logger.info("############### setCurrent() called: current = " + current);
        this.current = current;
    }
    
   
}