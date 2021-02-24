package app.repository.file;

import app.domain.Rezultat;

import java.util.List;

public class RezultatFile extends AbstractFileRepository<Integer, Rezultat> {
    public RezultatFile(String fileName){
        super(fileName);
    }

    @Override
    public Rezultat extractEntity(List<String> attributes) {
        Rezultat rezultat = new Rezultat(attributes.get(1), Double.parseDouble(attributes.get(2)));
        rezultat.setId(Integer.parseInt(attributes.get(0)));
        return rezultat;
    }

    @Override
    protected String createEntityAsString(Rezultat entity) {
        return entity.getId() + ";" + entity.getNumeStudent() + ";" + entity.getNota();
    }
}
