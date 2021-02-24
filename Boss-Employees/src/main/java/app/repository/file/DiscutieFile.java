package app.repository.file;

import app.domain.Discutie;
import app.domain.DiscutieSef;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DiscutieFile extends AbstractFileRepository<Integer, Discutie> {
    public DiscutieFile(String fileName){
        super(fileName);
    }

    @Override
    public Discutie extractEntity(List<String> attributes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(attributes.get(3), formatter);
        if(attributes.size() == 4) {
            Discutie discutie = new Discutie(attributes.get(1), attributes.get(2), dateTime);
            discutie.setId(Integer.parseInt(attributes.get(0)));
            return discutie;
        }
        else{
            Discutie discutie = new DiscutieSef(attributes.get(1), attributes.get(2), dateTime, attributes.get(3));
            discutie.setId(Integer.parseInt(attributes.get(0)));
            return discutie;
        }
    }

    @Override
    protected String createEntityAsString(Discutie entity) {
        return entity.getId() + ";" + entity.getNumeExpeditor() + ";" + entity.getMesaj() + ";" + entity.getOra();
    }
}
