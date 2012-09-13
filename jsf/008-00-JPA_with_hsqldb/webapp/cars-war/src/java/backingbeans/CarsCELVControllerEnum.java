package backingbeans;

import java.util.logging.Logger;

public enum CarsCELVControllerEnum implements java.io.Serializable {
    LIST, OPEN_FOR_CREATION;

    private static final String CLASSNAME=CarsCELVControllerEnum.class.getName();
    private static final Logger l = Logger.getLogger(CLASSNAME);


    public boolean getRenderNewItem() {
        boolean retValue;
        switch (this) {
            case LIST             : retValue = false;break;
            case OPEN_FOR_CREATION: retValue = true ;break;
            default               : throw new RuntimeException();
        }
        l.info("getRenderNewItem() returning: "+retValue);
        return retValue;
    }

    public boolean getRenderItemList() {
        boolean retValue;
        switch (this) {
            case LIST             : retValue =  true; break;
            case OPEN_FOR_CREATION: retValue =  false;break;
            default               : throw new RuntimeException();
        }
        l.info("getRenderItemList() returning: "+retValue);
        return retValue;
    }
}