package app.service;

import app.domain.Intrebare;
import app.domain.Raspuns;
import app.repository.InMemoryRepository;
import app.repository.Repository;
import app.repository.file.IntrebareFile;
import app.utils.events.Event;
import app.utils.events.IntrebareChangeEvent;
import app.utils.observer.Observable;
import app.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IntrebareService implements Observable<Event> {
    private Repository<Integer, Intrebare> repo;
    private Repository<Integer, Intrebare> repoTest;
    //private Repository<Integer, Raspuns> repoRaspuns;

    public IntrebareService(Repository<Integer, Intrebare> repo)
    {
        this.repo = repo;
        this.repoTest = new InMemoryRepository<>();
        //this.repoRaspuns = new InMemoryRepository<>();
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

    public List<Intrebare> getAllIntrebari() {
        Iterable<Intrebare> intrebari = repo.findAll();
        return StreamSupport.stream(intrebari.spliterator(), false).collect(Collectors.toList());
    }

    public List<Intrebare> getAllIntrebariTest() {
        Iterable<Intrebare> intrebari = repoTest.findAll();
        return StreamSupport.stream(intrebari.spliterator(), false).collect(Collectors.toList());
    }


    public Optional<Intrebare> addIntrebare(Intrebare intrebare){
        /*Integer idMax = StreamSupport.stream(this.repo.findAll().spliterator(), false).map(
                u -> u.getId()
        ).max(Integer::compareTo).get();

        if(idMax == null)
            idMax = 1;
        else
            idMax++;
        intrebare.setId(idMax);
        */
        Optional<Intrebare> intrebarePusa = repoTest.save(intrebare);

        notifyObservers(new IntrebareChangeEvent(null, intrebare));

        return intrebarePusa;
    }

    public Optional<Intrebare> deleteIntrebare(Intrebare intrebare) {
        for(Intrebare intr : getAllIntrebari()) {
            if (intr.equals(intrebare)) {
                Optional<Intrebare> intrebareRet = repo.delete(intr.getId());
                notifyObservers(null);
                return intrebareRet;
            };
        }
        notifyObservers(null);
        return Optional.empty();
    }

    public Intrebare getIntrebareByID(String id) {
        return StreamSupport.stream(repo.findAll().spliterator(),false)
                .filter(x-> x.getId().equals(id))
                .findFirst()
                .get();
    }

    public Boolean intrebareAleasa(Intrebare intrebare){
        return repoTest.findOne(intrebare.getId()).isPresent();
    }

    public double punctajRaspuns(int nrIntrebare, String raspuns){
        Intrebare intrebare = repo.findOne(nrIntrebare).get();
        if(intrebare.getRaspunsCorect().equals(raspuns))
            return intrebare.getPunctaj();
        return 0;
    }
}
