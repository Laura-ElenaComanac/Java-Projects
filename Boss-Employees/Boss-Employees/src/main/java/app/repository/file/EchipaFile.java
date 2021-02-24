package app.repository.file;

import app.domain.Echipa;

import java.util.List;

public class EchipaFile extends AbstractFileRepository<Integer, Echipa> {
    public EchipaFile(String fileName){
        super(fileName);
    }

    @Override
    public Echipa extractEntity(List<String> attributes) {
        Echipa echipa = new Echipa(attributes.get(1), attributes.get(2));
        echipa.setId(Integer.parseInt(attributes.get(0)));
        return echipa;
    }

    @Override
    protected String createEntityAsString(Echipa entity) {
        return entity.getId() + ";" + entity.getNume() + ";" + entity.getRol();
    }
}
