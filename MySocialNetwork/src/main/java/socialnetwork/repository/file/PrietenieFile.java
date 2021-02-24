package socialnetwork.repository.file;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.PrietenieValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrietenieFile extends AbstractFileRepository<Tuple<Long,Long>, Prietenie> {

    public PrietenieFile(String fileName, PrietenieValidator validator) {
        super(fileName, validator);
    }

    @Override
    public Prietenie extractEntity(List<String> attributes) {
        Long ID1 = Long.parseLong(attributes.get(0));
        Long ID2 = Long.parseLong(attributes.get(1));

        String date = attributes.get(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        Prietenie prietenie = new Prietenie(ID1, ID2, dateTime);
        Tuple<Long,Long> tuple = new Tuple<>(ID1, ID2);
        prietenie.setId(tuple);
        return prietenie;
    }

    @Override
    protected String createEntityAsString(Prietenie prietenie) {
        return prietenie.getID1()+";"+prietenie.getID2()+";"+prietenie.getDate().toString().replace('T',' ');
    }
}
