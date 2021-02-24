package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.exceptions.AlreadyExistentEntityException;
import socialnetwork.domain.exceptions.ValidationException;
import socialnetwork.repository.Repository;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observable;
import socialnetwork.utils.observer.Observer;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class UtilizatorService implements Observable<UtilizatorChangeEvent> {

    private Repository<Long, Utilizator> repo;
    private Repository<Tuple<Long,Long>, Prietenie> repoPrietenie;

    public UtilizatorService(Repository<Long, Utilizator> repo, Repository<Tuple<Long,Long>, Prietenie> repoPrietenie) {
        this.repo = repo;
        this.repoPrietenie = repoPrietenie;
    }

    private List<Observer<UtilizatorChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<UtilizatorChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<UtilizatorChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(UtilizatorChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public Optional<Utilizator> addUtilizator(Utilizator utilizator) {
        Long idMax = StreamSupport.stream(this.repo.findAll().spliterator(),false).map(
                u -> u.getId()
        ).max(Long::compareTo).get();

        if(idMax == null)
            idMax = 1L;
        else
            idMax++;
        utilizator.setId(idMax);

        try {
            return repo.save(utilizator);
        }
        catch (ValidationException e){
            throw new AlreadyExistentEntityException("Utilizator deja existent!");
        }
    }

    public Optional<Utilizator> deleteUtilizator(Utilizator utilizator) {
        for(Utilizator util : getAllUsers()) {
            if (util.equals(utilizator)) {
                List<Prietenie> desters = new LinkedList<>();
                for(Prietenie prietenie : repoPrietenie.findAll()) {
                    if (prietenie.getID1() == util.getId()) {
                        desters.add(prietenie);
                        Utilizator utilizator2= repo.findOne(prietenie.getID2()).get();
                        List<Utilizator> newFriends = utilizator2.getFriends();
                        newFriends.remove(util);
                        utilizator2.setFriends(newFriends);
                        repo.update(utilizator2);
                    }
                    if(prietenie.getID2() == util.getId()){
                        desters.add(prietenie);
                        Utilizator utilizator1= repo.findOne(prietenie.getID1()).get();
                        List<Utilizator> newFriends = utilizator1.getFriends();
                        newFriends.remove(util);
                        utilizator1.setFriends(newFriends);
                        repo.update(utilizator1);
                    }
                }
                for(Prietenie prietenie : desters)
                    repoPrietenie.delete(prietenie.getId());
                return repo.delete(util.getId());
            }
        }
        return Optional.empty();
    }

    public Optional<Utilizator> updateUtilizator(Utilizator utilizator, Utilizator utilizatorNou) {
        for(Utilizator util : getAllUsers()) {
            if (util.equals(utilizator)) {
                utilizatorNou.setId(util.getId());
                return repo.update(utilizatorNou);
            }
        }
        return Optional.empty();
    }

    public List<Utilizator> getAllUsers() {
        Iterable<Utilizator> utilizatori = repo.findAll();
        return StreamSupport.stream(utilizatori.spliterator(), false).collect(Collectors.toList());
    }


    public Utilizator getUserByName(String nume, String prenume) {
        return StreamSupport.stream(repo.findAll().spliterator(),false)
                .filter(x-> x.getFirstName().equals(nume) && x.getLastName().equals(prenume))
                .findFirst()
                .get();
    }

    public Utilizator getUserById(Long Id){
        return this.repo.findOne(Id).get();
    }
}
