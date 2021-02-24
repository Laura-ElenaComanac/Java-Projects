package app.utils.events;

import app.domain.Reservation;

public class ReservationChangeEvent implements Event{
    private ChangeEventType type;
    private Reservation data, oldData;

    public ReservationChangeEvent(ChangeEventType type, Reservation data) {
        this.type = type;
        this.data = data;
    }
    public ReservationChangeEvent(ChangeEventType type, Reservation data, Reservation oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Reservation getData() {
        return data;
    }

    public Reservation getOldData() {
        return oldData;
    }
}
