package app.service;

import app.domain.Echipa;
import app.repository.Repository;
import app.utils.events.ChangeEventType;
import app.utils.events.DiscutieChangeEvent;
import app.utils.events.EchipaChangeEvent;

import java.util.stream.StreamSupport;

public class EchipaService extends Service<Integer, Echipa, EchipaChangeEvent> {

    public EchipaService(Repository<Integer, Echipa> repo) {
        super(repo);
    }

    @Override
    protected EchipaChangeEvent createNewEvent(ChangeEventType type, Echipa echipa) {
        return new EchipaChangeEvent(type, echipa);
    }

    public Echipa getUserByName(String nume) {
        return StreamSupport.stream(repo.findAll().spliterator(),false)
                .filter(x-> x.getNume().equals(nume))
                .findFirst()
                .get();
    }

    public Boolean membruAles(Echipa membru){
        return repo.findOne(membru.getId()).isPresent();
    }
}
