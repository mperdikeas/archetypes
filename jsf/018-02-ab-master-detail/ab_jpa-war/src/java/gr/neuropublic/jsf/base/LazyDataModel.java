/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.neuropublic.jsf.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Overriden class which overcomes bug with Arithmetic exception.
 *
 * @author g_dimitriadis
 */
public abstract class LazyDataModel extends org.primefaces.model.LazyDataModel {
    
    private static Logger logger = LoggerFactory.getLogger(LazyDataModel.class);

    @Override
    public void setRowIndex(int rowIndex) {
        try {
            super.setRowIndex(rowIndex);
        }
        catch(ArithmeticException e) {
            logger.error("java.lang.ArithmeticException is ignored by application.");
        }
    }
}