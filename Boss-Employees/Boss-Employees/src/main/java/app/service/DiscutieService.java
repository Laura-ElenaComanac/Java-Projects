package app.service;

import app.domain.Discutie;
import app.repository.Repository;
import app.utils.events.ChangeEventType;
import app.utils.events.DiscutieChangeEvent;

public class DiscutieService extends Service<Integer, Discutie, DiscutieChangeEvent> {

    public DiscutieService(Repository<Integer, Discutie> repo) {
        super(repo);
    }

    @Override
    protected DiscutieChangeEvent createNewEvent(ChangeEventType type, Discutie discutie) {
        return new DiscutieChangeEvent(type, discutie);
    }


}
