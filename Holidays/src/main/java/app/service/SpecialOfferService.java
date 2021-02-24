package app.service;

import app.domain.SpecialOffer;
import app.repository.Repository;
import app.utils.events.ChangeEventType;
import app.utils.events.SpecialOfferChangeEvent;

public class SpecialOfferService extends Service<Double, SpecialOffer, SpecialOfferChangeEvent>{
    public SpecialOfferService(Repository<Double, SpecialOffer> repo) {
        super(repo);
    }

    @Override
    protected SpecialOfferChangeEvent createNewEvent(ChangeEventType type, SpecialOffer specialOffer) {
        return new SpecialOfferChangeEvent(type,specialOffer);
    }
}
