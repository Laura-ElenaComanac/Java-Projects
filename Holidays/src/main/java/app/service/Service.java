package app.service;

import app.domain.Entity;
import app.repository.Repository;
import app.utils.events.ChangeEventType;
import app.utils.events.Event;
import app.utils.observer.Observable;
import app.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class Service<ID, E extends Entity<ID>, ChangeEvent extends Event> implements Observable<Event>{
    protected Repository<ID,E> repo;

    public Service(Repository<ID,E> repo){
        this.repo = repo;
    }

    private List<Observer<Event>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<Event> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<Event> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(Event t) {
        observers.stream().forEach(x->x.update((t)));
    }

    public List<E> getAllEntities() {
        Iterable<E> entities = repo.findAll();
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toList());
    }

    public Optional<E> addEntity(E e){
        /*Integer idMax = StreamSupport.stream(this.repo.findAll().spliterator(), false).map(
                u -> u.getId()
        ).max(Integer::compareTo).get();

        if(idMax == null)
            idMax = 1;
        else
            idMax++;
        intrebare.setId(idMax);
        */
        Optional<E> entitateSalvata = repo.save(e);

        notifyObservers(createNewEvent(ChangeEventType.ADD, e));

        return entitateSalvata;
    }

    public Optional<E> deleteEntity(E e) {
        for(E i : getAllEntities()) {
            if (i.equals(e)) {
                Optional<E> eRet = repo.delete(i.getId());
                notifyObservers(createNewEvent(ChangeEventType.DELETE,e));
                return eRet;
            };
        }
        notifyObservers(createNewEvent(ChangeEventType.DELETE,e));
        return Optional.empty();
    }

    public E findOne(ID id){
        return repo.findOne(id).get();
    }

    protected abstract ChangeEvent createNewEvent(ChangeEventType type, E e);
}
