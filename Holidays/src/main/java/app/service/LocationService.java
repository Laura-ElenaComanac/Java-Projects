package app.service;

import app.domain.Location;
import app.repository.Repository;
import app.utils.events.ChangeEventType;
import app.utils.events.LocationChangeEvent;

public class LocationService extends Service<Double, Location, LocationChangeEvent>{
    public LocationService(Repository<Double, Location> repo) {
        super(repo);
    }

    @Override
    protected LocationChangeEvent createNewEvent(ChangeEventType type, Location location) {
        return new LocationChangeEvent(type,location);
    }
}
