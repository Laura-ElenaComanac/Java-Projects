package app.service;

import app.domain.Raspuns;
import app.domain.Rezultat;
import app.repository.Repository;
import app.utils.events.Event;
import app.utils.events.RezultatChangeEvent;
import app.utils.observer.Observable;
import app.utils.observer.Observer;
import com.sun.javafx.collections.MappingChange;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RezultatService implements Observable<Event> {
    private Repository<Integer, Rezultat> repo;

    public RezultatService(Repository<Integer, Rezultat> repo)
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

    public List<Rezultat> getAllRezultate(){
        Iterable<Rezultat> rezultate = repo.findAll();
        return StreamSupport.stream(rezultate.spliterator(), false).collect(Collectors.toList());
    }

    public Optional<Rezultat> addRezultat(Rezultat rezultat){
        /*Integer idMax = StreamSupport.stream(this.repo.findAll().spliterator(), false).map(
                u -> u.getId()
        ).max(Integer::compareTo).get();

        if(idMax == null)
            idMax = 1;
        else
            idMax++;
        intrebare.setId(idMax);
        */
        Optional<Rezultat> rezultatPus = repo.save(rezultat);

        notifyObservers(new RezultatChangeEvent(null, rezultat));

        return rezultatPus;
    }

    public Optional<Rezultat> deleteRezultat(Rezultat rezultat) {
        for(Rezultat rez : getAllRezultate()) {
            if (rez.equals(rezultat)) {
                Optional<Rezultat> rezultatRet = repo.delete(rez.getId());
                notifyObservers(null);
                return rezultatRet;
            };
        }
        notifyObservers(null);
        return Optional.empty();
    }

    public List<Rezultat> calculeazaNote(List<Raspuns> raspunsuri)
    {
        List<Rezultat> note = new LinkedList<>();
        HashMap<String,Double> studentRaspunsuri = new HashMap<>();

        for(Raspuns raspuns : raspunsuri) {
            if(studentRaspunsuri.containsKey(raspuns.getNumeStudent())) {
                Double acumulat = studentRaspunsuri.get(raspuns.getNumeStudent());
                studentRaspunsuri.put(raspuns.getNumeStudent(), acumulat + raspuns.getPunctaj());
            }
            else
                studentRaspunsuri.put(raspuns.getNumeStudent(), raspuns.getPunctaj());
        }

        for(Map.Entry<String,Double> entry : studentRaspunsuri.entrySet())
            note.add(new Rezultat(entry.getKey(), entry.getValue()));
        return note;
    }
}
