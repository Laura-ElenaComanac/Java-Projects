package app.service;

import app.domain.Hotel;
import app.domain.exceptions.ValidationException;
import app.domain.validators.Validator;
import app.repository.Repository;
import app.utils.events.ChangeEventType;
import app.utils.events.HotelChangeEvent;

public class HotelService extends Service<Double, Hotel, HotelChangeEvent> {
    Validator<Hotel> validator = hotel->{
        if(hotel.getHotelId() < 0)
            throw new ValidationException("nu poate fi vid!");
    };

    public HotelService(Repository<Double, Hotel> repo) {
        super(repo);
    }

    @Override
    protected HotelChangeEvent createNewEvent(ChangeEventType type, Hotel hotel) {
        return new HotelChangeEvent(type, hotel);
    }
}
