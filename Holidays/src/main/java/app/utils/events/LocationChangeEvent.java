package app.utils.events;

import app.domain.Location;

public class LocationChangeEvent implements Event{
    private ChangeEventType type;
    private Location data, oldData;

    public LocationChangeEvent(ChangeEventType type, Location data) {
        this.type = type;
        this.data = data;
    }
    public LocationChangeEvent(ChangeEventType type, Location data, Location oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Location getData() {
        return data;
    }

    public Location getOldData() {
        return oldData;
    }
}
