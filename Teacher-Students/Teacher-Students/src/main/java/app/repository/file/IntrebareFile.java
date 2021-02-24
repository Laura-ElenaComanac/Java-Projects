package app.repository.file;

import app.domain.Intrebare;

import java.util.List;

public class IntrebareFile extends AbstractFileRepository<Integer, Intrebare> {
    public IntrebareFile(String fileName){
        super(fileName);
    }

    @Override
    public Intrebare extractEntity(List<String> attributes) {
        Intrebare intrebare = new Intrebare(attributes.get(1), attributes.get(2),attributes.get(3),attributes.get(4),attributes.get(5),Integer.parseInt(attributes.get(6)));
        intrebare.setId(Integer.parseInt(attributes.get(0)));
        return intrebare;
    }

    @Override
    protected String createEntityAsString(Intrebare entity) {
        return entity.getId() + ";" + entity.getDescriere() + ";" + entity.getV1() + ";" + entity.getV2() + ";" + entity.getV3() + ";" + entity.getRaspunsCorect() + ";" + entity.getPunctaj();
    }
}
