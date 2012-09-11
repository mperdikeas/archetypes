package controllers;

import java.util.logging.Logger;

public enum UserLControllerEnum implements java.io.Serializable {
    LIST, OPEN_FOR_CREATION;

    private static final String CLASSNAME=UserLControllerEnum.class.getName();
    private static final Logger l = Logger.getLogger(CLASSNAME);


    public boolean getRenderNewItemGrid() {
        switch (this) {
            case LIST             : return false;
            case OPEN_FOR_CREATION: return true;
            default               : throw new RuntimeException();
        }
    }

    public boolean getRenderBasketList() {
        l.info("getRenderBasketList: enum is: "+this);
        switch (this) {
        case LIST             : l.info(" returning true **************** "); return true;
            case OPEN_FOR_CREATION: return false;
            default               : throw new RuntimeException();
        }
    }
}