package app.utils.events;

import app.domain.Hotel;

public class HotelChangeEvent implements Event {
    private ChangeEventType type;
    private Hotel data, oldData;

    public HotelChangeEvent(ChangeEventType type, Hotel data) {
        this.type = type;
        this.data = data;
    }
    public HotelChangeEvent(ChangeEventType type, Hotel data, Hotel oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Hotel getData() {
        return data;
    }

    public Hotel getOldData() {
        return oldData;
    }
}
