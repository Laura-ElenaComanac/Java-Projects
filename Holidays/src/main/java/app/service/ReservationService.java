package app.service;

import app.domain. Reservation;
import app.repository.Repository;
import app.utils.events.ChangeEventType;
import app.utils.events. ReservationChangeEvent;

public class ReservationService extends Service<Double,  Reservation, ReservationChangeEvent>{
    public ReservationService(Repository<Double, Reservation> repo) {
        super(repo);
    }

    @Override
    protected ReservationChangeEvent createNewEvent(ChangeEventType type, Reservation reservation) {
        return new ReservationChangeEvent(type,reservation);
    }

    public int size(){
        return repo.size();
    }

}
