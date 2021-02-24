package app.utils.events;

import app.domain.Rezultat;

public class RezultatChangeEvent implements Event{
    private ChangeEventType type;
    private Rezultat data, oldData;

    public RezultatChangeEvent(ChangeEventType type, Rezultat data) {
        this.type = type;
        this.data = data;
    }
    public RezultatChangeEvent(ChangeEventType type, Rezultat data, Rezultat oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Rezultat getData() {
        return data;
    }

    public Rezultat getOldData() {
        return oldData;
    }
}
