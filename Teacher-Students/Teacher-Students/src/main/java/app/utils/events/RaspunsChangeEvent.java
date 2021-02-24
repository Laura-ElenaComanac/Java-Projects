package app.utils.events;

import app.domain.Raspuns;

public class RaspunsChangeEvent implements Event{
    private ChangeEventType type;
    private Raspuns data, oldData;

    public RaspunsChangeEvent(ChangeEventType type, Raspuns data) {
        this.type = type;
        this.data = data;
    }
    public RaspunsChangeEvent(ChangeEventType type, Raspuns data, Raspuns oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Raspuns getData() {
        return data;
    }

    public Raspuns getOldData() {
        return oldData;
    }
}
