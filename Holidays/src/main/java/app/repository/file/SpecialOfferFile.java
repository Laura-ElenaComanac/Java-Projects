package app.repository.file;

import app.domain.SpecialOffer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class SpecialOfferFile extends AbstractFileRepository<Double, SpecialOffer>{

    public SpecialOfferFile(String fileName) {
        super(fileName);
    }

    @Override
    public SpecialOffer extractEntity(List<String> attributes)throws Exception {
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        SpecialOffer specialOffer = new SpecialOffer(Double.parseDouble(attributes.get(0)), Double.parseDouble(attributes.get(1)), formatter.parse(attributes.get(2)), formatter.parse(attributes.get(3)), Integer.parseInt(attributes.get(4)));
        specialOffer.setId(Double.parseDouble(attributes.get(0)));
        return specialOffer;
    }

    @Override
    protected String createEntityAsString(SpecialOffer entity) {
        return entity.getSpecialOfferId() + ";" + entity.getHotelId() + ";" + entity.getStartDate() + ";" + entity.getEndDate() + ";" + entity.getPercents();
    }
}
