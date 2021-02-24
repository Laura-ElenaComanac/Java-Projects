package app.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class SpecialOffer extends Entity<Double> {
    Double specialOfferId;
    Double hotelId;
    Date startDate;
    Date endDate;
    Integer percents;

    static AtomicInteger identity = new AtomicInteger(0);

    public SpecialOffer(Double specialOfferId, Double hotelId, Date startDate, Date endDate, Integer percents) {
        this.specialOfferId = specialOfferId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percents = percents;

        this.setId(identity.incrementAndGet()+0.0);
    }

    public Double getSpecialOfferId() {
        return specialOfferId;
    }

    public Double getHotelId() {
        return hotelId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Integer getPercents() {
        return percents;
    }

    public static AtomicInteger getIdentity() {
        return identity;
    }

    public void setSpecialOfferId(Double specialOfferId) {
        this.specialOfferId = specialOfferId;
    }

    public void setHotelId(Double hotelId) {
        this.hotelId = hotelId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPercents(Integer percents) {
        this.percents = percents;
    }

    public static void setIdentity(AtomicInteger identity) {
        SpecialOffer.identity = identity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialOffer that = (SpecialOffer) o;
        return specialOfferId.equals(that.specialOfferId) && hotelId.equals(that.hotelId) && startDate.equals(that.startDate) && endDate.equals(that.endDate) && percents.equals(that.percents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialOfferId, hotelId, startDate, endDate, percents);
    }
}
