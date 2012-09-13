package backingbeans;

import java.util.logging.Logger;

public enum CELVControllerEnum implements java.io.Serializable {
    LIST, OPEN_FOR_CREATION, OPEN_FOR_MOD;

    private static final String CLASSNAME = CELVControllerEnum.class.getName();
    private static final Logger l = Logger.getLogger(CLASSNAME);

    private boolean expects(CELVControllerEnum expectedValue) {
        if (this.equals(expectedValue)) return true;
        else return false;
    }

    public boolean getRenderNewItem() {
        return expects(CELVControllerEnum.OPEN_FOR_CREATION);
    }

    public boolean getRenderModItem() {
        return expects(CELVControllerEnum.OPEN_FOR_MOD);
    }

    public boolean getRenderItemList() {
        return expects(CELVControllerEnum.LIST);
    }
}