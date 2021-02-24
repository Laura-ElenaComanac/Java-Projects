package app.domain;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Location extends Entity<Double>{
    Double locationId;
    String locationName;

    static AtomicInteger identity = new AtomicInteger(0);

    public Location(Double locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;

        this.setId(identity.incrementAndGet()+0.0);
    }

    public void setLocationId(Double locationId) {
        this.locationId = locationId;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Double getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationId.equals(location.locationId) && locationName.equals(location.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, locationName);
    }

    @Override
    public String toString() {
        return  locationName ;
    }
}
