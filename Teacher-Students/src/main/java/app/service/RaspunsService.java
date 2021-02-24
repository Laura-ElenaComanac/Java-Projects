package app.service;

import app.domain.Raspuns;
import app.repository.Repository;
import app.repository.file.RaspunsFile;
import app.utils.events.Event;
import app.utils.events.RaspunsChangeEvent;
import app.utils.observer.Observable;
import app.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RaspunsService implements Observable<Event> {
    private RaspunsFile repo;

    public RaspunsService(RaspunsFile repo)
    {
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

    public List<Raspuns> getAllRaspunsuri(){
        Iterable<Raspuns> raspunsuri = repo.findAll();
        return StreamSupport.stream(raspunsuri.spliterator(), false).collect(Collectors.toList());
    }

    public Optional<Raspuns> addRaspuns(Raspuns raspuns){
        /*Integer idMax = StreamSupport.stream(this.repo.findAll().spliterator(), false).map(
                u -> u.getId()
        ).max(Integer::compareTo).get();

        if(idMax == null)
            idMax = 1;
        else
            idMax++;
        intrebare.setId(idMax);
        */
        Optional<Raspuns> raspunsPus = repo.save(raspuns);

        notifyObservers(new RaspunsChangeEvent(null, raspuns));

        return raspunsPus;
    }

    public void appendNumber(Integer nr){
        repo.appendNumber(nr);
    }
}
