package app.utils.events;

import app.domain.Intrebare;

public class IntrebareChangeEvent implements Event {
    private ChangeEventType type;
    private Intrebare data, oldData;

    public IntrebareChangeEvent(ChangeEventType type, Intrebare data) {
        this.type = type;
        this.data = data;
    }
    public IntrebareChangeEvent(ChangeEventType type, Intrebare data, Intrebare oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Intrebare getData() {
        return data;
    }

    public Intrebare getOldData() {
        return oldData;
    }
}
