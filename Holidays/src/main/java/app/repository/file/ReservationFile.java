package app.repository.file;

import app.domain.Reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservationFile extends AbstractFileRepository<Double, Reservation>{
    public ReservationFile(String fileName) {
        super(fileName);
    }

    @Override
    public Reservation extractEntity(List<String> attributes) {
        String date = attributes.get(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        Reservation reservation = new Reservation(Double.parseDouble(attributes.get(0)), Long.parseLong(attributes.get(1)), Double.parseDouble(attributes.get(2)), dateTime, Integer.parseInt(attributes.get(4)));
        reservation.setId(Double.parseDouble(attributes.get(0)));
        return reservation;
    }

    @Override
    protected String createEntityAsString(Reservation entity) {
        return entity.getReservationId() + ";" + entity.getClientId() + ";" + entity.getHotelId() + ";" + entity.getStartDate() + ";" + entity.getNoNights();
    }
}
