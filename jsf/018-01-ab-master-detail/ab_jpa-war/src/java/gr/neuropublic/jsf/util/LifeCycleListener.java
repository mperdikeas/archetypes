package gr.neuropublic.jsf.util;


import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import java.util.logging.Logger;

public class LifeCycleListener implements PhaseListener {

    private final Logger l = Logger.getLogger(this.getClass().getName());

    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    public void beforePhase(PhaseEvent event) {
        l.info("START PHASE: " + event.getPhaseId());
    }

    public void afterPhase(PhaseEvent event) {
        l.info("END PHASE: " + event.getPhaseId());
    }
}