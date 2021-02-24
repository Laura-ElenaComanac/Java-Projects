package socialnetwork.utils.events;

import socialnetwork.domain.Utilizator;

public class UtilizatorStatusEvent implements Event {
    private UtilizatorExecutionStatusEventType type;
    private Utilizator utilizator;

    public UtilizatorStatusEvent(UtilizatorExecutionStatusEventType type, Utilizator utilizator) {
        this.utilizator=utilizator;
        this.type=type;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    public UtilizatorExecutionStatusEventType getType() {
        return type;
    }

    public void setType(UtilizatorExecutionStatusEventType type) {
        this.type = type;
    }
}
