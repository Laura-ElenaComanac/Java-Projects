package app.utils.events;

import app.domain.Discutie;

public class DiscutieChangeEvent implements Event {
    private ChangeEventType type;
    private Discutie data, oldData;

    public DiscutieChangeEvent(ChangeEventType type, Discutie data) {
        this.type = type;
        this.data = data;
    }
    public DiscutieChangeEvent(ChangeEventType type, Discutie data, Discutie oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Discutie getData() {
        return data;
    }

    public Discutie getOldData() {
        return oldData;
    }
}
