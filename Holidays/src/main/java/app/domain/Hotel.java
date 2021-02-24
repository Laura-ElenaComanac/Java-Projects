package app.domain;


import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Hotel extends Entity<Double> {
    Double hotelId;
    Double locationId;
    String hotelName;
    Integer noRooms;
    Double pricePerNight;
    public enum type{family,teenagers,oldpeople};
    type type;

    static AtomicInteger identity = new AtomicInteger(0);

    public Hotel(Double hotelId, Double locationId, String hotelName, Integer noRooms, Double pricePerNight, type type) {
        this.hotelId = hotelId;
        this.locationId = locationId;
        this.hotelName = hotelName;
        this.noRooms = noRooms;
        this.pricePerNight = pricePerNight;
        this.type = type;

        this.setId(identity.incrementAndGet()+0.0);
    }

    public Double getHotelId() {
        return hotelId;
    }

    public Double getLocationId() {
        return locationId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public Integer getNoRooms() {
        return noRooms;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public Hotel.type getType() {
        return type;
    }

    public static AtomicInteger getIdentity() {
        return identity;
    }

    public void setHotelId(Double hotelId) {
        this.hotelId = hotelId;
    }

    public void setLocationId(Double locationId) {
        this.locationId = locationId;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setNoRooms(Integer noRooms) {
        this.noRooms = noRooms;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setType(Hotel.type type) {
        this.type = type;
    }

    public static void setIdentity(AtomicInteger identity) {
        Hotel.identity = identity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, locationId, hotelName, noRooms, pricePerNight, type);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Hotel))
            return false;
        Hotel hotel = (Hotel) obj;
        return Objects.equals(getHotelId(), hotel.getHotelId()) &&
                Objects.equals(getLocationId(), hotel.getLocationId()) &&
                Objects.equals(getHotelName(), hotel.getHotelName()) &&
                Objects.equals(getNoRooms(), hotel.getNoRooms()) &&
                Objects.equals(getPricePerNight(), hotel.getPricePerNight()) &&
                Objects.equals(getType(), hotel.getType());
    }
}
