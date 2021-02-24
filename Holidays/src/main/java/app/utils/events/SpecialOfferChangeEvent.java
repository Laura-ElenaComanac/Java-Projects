package app.utils.events;

import app.domain.SpecialOffer;

public class SpecialOfferChangeEvent implements Event{
    private ChangeEventType type;
    private SpecialOffer data, oldData;

    public SpecialOfferChangeEvent(ChangeEventType type, SpecialOffer data) {
        this.type = type;
        this.data = data;
    }
    public SpecialOfferChangeEvent(ChangeEventType type, SpecialOffer data, SpecialOffer oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public SpecialOffer getData() {
        return data;
    }

    public SpecialOffer getOldData() {
        return oldData;
    }
}