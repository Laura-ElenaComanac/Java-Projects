package app.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Reservation extends Entity<Double>{
    Double reservationId;
    Long clientId;
    Double hotelId;
    LocalDateTime startDate;
    Integer noNights;

    static AtomicInteger identity = new AtomicInteger(0);

    public Reservation(Double reservationId, Long clientId, Double hotelId, LocalDateTime startDate, Integer noNights) {
        this.reservationId = reservationId;
        this.clientId = clientId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.noNights = noNights;

        this.setId(identity.incrementAndGet()+0.0);
    }

    public Double getReservationId() {
        return reservationId;
    }

    public Long getClientId() {
        return clientId;
    }

    public Double getHotelId() {
        return hotelId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Integer getNoNights() {
        return noNights;
    }

    public static AtomicInteger getIdentity() {
        return identity;
    }

    public void setReservationId(Double reservationId) {
        this.reservationId = reservationId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setHotelId(Double hotelId) {
        this.hotelId = hotelId;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setNoNights(Integer noNights) {
        this.noNights = noNights;
    }

    public static void setIdentity(AtomicInteger identity) {
        Reservation.identity = identity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return reservationId.equals(that.reservationId) && clientId.equals(that.clientId) && hotelId.equals(that.hotelId) && startDate.equals(that.startDate) && noNights.equals(that.noNights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, clientId, hotelId, startDate, noNights);
    }
}
