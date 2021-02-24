package app.repository.file;

import app.domain.Location;

import java.util.List;

public class LocationFile extends AbstractFileRepository<Double, Location>{

    public LocationFile(String fileName) {
        super(fileName);
    }

    @Override
    public Location extractEntity(List<String> attributes) {
        Location location = new Location(Double.parseDouble(attributes.get(0)),attributes.get(1));
        location.setId(Double.parseDouble(attributes.get(0)));
        return location;
    }

    @Override
    protected String createEntityAsString(Location entity) {
        return entity.getLocationId() + ";" + entity.getLocationName();
    }
}
