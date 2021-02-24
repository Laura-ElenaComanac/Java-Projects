package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.exceptions.AlreadyExistentEntityException;
import socialnetwork.domain.exceptions.NullEntityException;
import socialnetwork.domain.exceptions.ValidationException;
import socialnetwork.repository.Repository;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observable;
import socialnetwork.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PrietenieService implements Observable<UtilizatorChangeEvent> {

    private Repository<Long,Utilizator> repoUtilizator;
    private Repository<Tuple<Long,Long>, Prietenie> repoPrietenie;

    public PrietenieService(Repository<Long,Utilizator> repoUtilizator, Repository<Tuple<Long,Long>, Prietenie> repoPrietenie){
        this.repoUtilizator = repoUtilizator;
        this.repoPrietenie = repoPrietenie;

        for(Prietenie prietenie : repoPrietenie.findAll()) {
            Utilizator utilizator1 = repoUtilizator.findOne(prietenie.getID1()).get();
            Utilizator utilizator2 = repoUtilizator.findOne(prietenie.getID2()).get();

            List<Utilizator> lista;

            lista = utilizator1.getFriends();
            lista.add(utilizator2);
            utilizator1.setFriends(lista);

            lista = utilizator2.getFriends();
            lista.add(utilizator1);
            utilizator2.setFriends(lista);

//            repoUtilizator.update(utilizator1);
        }
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

    public Optional<Prietenie> addPrietenie(Utilizator utilizator1, Utilizator utilizator2, LocalDateTime date){
        List<Utilizator> utilizatori = new LinkedList<>();
        repoUtilizator.findAll().forEach(utilizatori::add);

        int index1 = utilizatori.indexOf(utilizator1);
        int index2 = utilizatori.indexOf(utilizator2);

        if(index1 == -1)
            throw new ValidationException("Primul utilizator nu exista!");
        if(index2 == -1)
            throw new ValidationException("Al doilea utilizator nu exista!");

        Prietenie prietenie = new Prietenie(utilizatori.get(index1).getId(), utilizatori.get(index2).getId(), date);

        try {
            Optional<Prietenie> optional =  repoPrietenie.save(prietenie);

            Utilizator firstUtilizator = repoUtilizator.findOne(prietenie.getID1()).get();
            Utilizator secondUtilizator = repoUtilizator.findOne(prietenie.getID2()).get();

            List<Utilizator> lista;

            lista = firstUtilizator.getFriends();
            lista.add(secondUtilizator);
            firstUtilizator.setFriends(lista);

            lista = secondUtilizator.getFriends();
            lista.add(firstUtilizator);
            secondUtilizator.setFriends(lista);

            return optional;
        }
        catch (ValidationException e){
            throw new AlreadyExistentEntityException("Prietenie deja existenta!");
        }
    }

    public Optional<Prietenie> deletePrietenie(Utilizator utilizator1, Utilizator utilizator2, LocalDateTime date) {
        Optional<Utilizator> utilizatorNou1 = StreamSupport.stream(repoUtilizator.findAll().spliterator(), false)
                .filter(u->u.equals(utilizator1))
                .findFirst();
        Optional<Utilizator> utilizatorNou2 = StreamSupport.stream(repoUtilizator.findAll().spliterator(), false)
                .filter(u->u.equals(utilizator2))
                .findFirst();

        if(!utilizatorNou1.isPresent())
            throw new NullEntityException("Primul utilizator nu exista!");
        if(!utilizatorNou2.isPresent())
            throw new NullEntityException("Al doilea utilizator nu exista!");

        Prietenie prietenie = new Prietenie(utilizatorNou1.get().getId(), utilizatorNou2.get().getId(),date);

        for(Prietenie prieten : getAllFriendships()) {
            if (prieten.equals(prietenie)) {
                utilizatorNou1.get().getFriends().remove(utilizatorNou2.get());
                utilizatorNou2.get().getFriends().remove(utilizatorNou1.get());
                return repoPrietenie.delete(prieten.getId());
            }
        }
        return Optional.empty();
    }

    public List<Tuple<Utilizator,LocalDateTime>> getFriendDate(String nume, String prenume){
//        Long id;
//        Tuple<Utilizator, LocalDateTime> tuple;

//        for(Utilizator util : repoUtilizator.findAll()) {
//            if (util.equals(utilizator)) {
//                id = util.getId();
//                for (Prietenie prietenie : repoPrietenie.findAll()){
//                    if (prietenie.getID1().equals(id)) {
//                        tuple = new Tuple(repoUtilizator.findOne(prietenie.getID2()).get(), prietenie.getDate());
//                        lista.add(tuple);
//                    }
//                    if (prietenie.getID2().equals(id)) {
//                        tuple = new Tuple(repoUtilizator.findOne(prietenie.getID1()).get(), prietenie.getDate());
//                        lista.add(tuple);
//                    }
//                }
//            }
//        }

        List<Tuple<Utilizator,LocalDateTime>> lista = new LinkedList<>();
        Utilizator utilizator = new Utilizator(nume, prenume);
        Iterable<Utilizator> all = repoUtilizator.findAll();

        Predicate<Utilizator> byUtilizator = x->x.equals(utilizator);

        StreamSupport.stream(all.spliterator(),false)
                .filter(byUtilizator)
                .findFirst().ifPresent(myUtil -> myUtil.getFriends().stream()
                    .map(friend -> {
                        Optional<Prietenie> optionalPrietenie = this.repoPrietenie.findOne(new Tuple<>(myUtil.getId(), friend.getId()));
                        return optionalPrietenie.map(prietenie -> new Tuple<>(friend, prietenie.getDate())).orElseGet(() -> new Tuple<>(friend, this.repoPrietenie.findOne(new Tuple<>(friend.getId(), myUtil.getId())).get().getDate()));
                    })
                .forEach(lista::add)
        );

        if(lista.isEmpty())
            throw new NullEntityException("Utilizatorul nu exista sau nu are prieteni!");

        return lista;
    }

    public List<Tuple<Utilizator,LocalDateTime>> getFriendDateMonth(String nume, String prenume, String luna){
        List<Tuple<Utilizator,LocalDateTime>> lista = new LinkedList<>();
        Utilizator utilizator = new Utilizator(nume, prenume);
        Iterable<Utilizator> all = repoUtilizator.findAll();

        Predicate<Utilizator> byUtilizator = x->x.equals(utilizator);
        //Predicate<Utilizator> byMonth = x->x.get.equals(luna);

        StreamSupport.stream(all.spliterator(), false)
                .filter(byUtilizator)
                .findFirst().ifPresent(myUtil -> myUtil.getFriends().stream()
                .map(friend -> {
                    Optional<Prietenie> optionalPrietenie = this.repoPrietenie.findOne(new Tuple<>(myUtil.getId(), friend.getId()));
                    return optionalPrietenie.map(prietenie -> new Tuple<>(friend, prietenie.getDate())).orElseGet(() -> new Tuple<>(friend, this.repoPrietenie.findOne(new Tuple<>(friend.getId(), myUtil.getId())).get().getDate()));
                })
                    //.map(friend -> new Tuple<>(friend, this.repoPrietenie.findOne(new Tuple<>(myUtil.getId(), friend.getId())).get().getDate()))
                .filter(friend -> friend.getRight().toString().substring(5,7).equals(luna))
                .forEach(lista::add)
        );

        if(lista.isEmpty())
            throw new NullEntityException("Utilizatorul nu exista sau nu are prieteni in aceasta luna!");

        return lista;
    }

    public List<Prietenie> getAllFriendships() {
        Iterable<Prietenie> prietenii = repoPrietenie.findAll();
        return StreamSupport.stream(prietenii.spliterator(), false).collect(Collectors.toList());
    }

    public LinkedList<LinkedList<Utilizator>> comunitati(){ // returneaza toate componentele conexe (comunitatile)
        LinkedList<Utilizator> utilizatori = new LinkedList<>();
        repoUtilizator.findAll().forEach(utilizatori::add);

        LinkedList<LinkedList<Utilizator>> componenteConexe = new LinkedList<>();

        while(!utilizatori.isEmpty()) { // DFS
            LinkedList<Utilizator> componentaCurenta = new LinkedList<>();
            ArrayDeque<Utilizator> stack = new ArrayDeque<>();

            Utilizator utilizatorCurent = utilizatori.pop();

            stack.push(utilizatorCurent);

            while(!stack.isEmpty()){
                utilizatorCurent = stack.pop();
                if(!componentaCurenta.contains(utilizatorCurent)) {
                    componentaCurenta.add(utilizatorCurent);
                    utilizatori.remove(utilizatorCurent);
                    utilizatorCurent.getFriends().forEach(stack::push);
                }
            }
            componenteConexe.add(componentaCurenta);
        }
        return componenteConexe;
    }

    boolean valid(LinkedList<Utilizator> lantUtilizatori){// verifica daca ultimul utilizator este prieten cu penultimul utilizator din lant
        if(lantUtilizatori.size() <= 1)
            return true;
        return lantUtilizatori.getLast().getFriends().contains(lantUtilizatori.get(lantUtilizatori.size()-2));
    }

     class IntHolder{// referinta int
        int myInt;

        public IntHolder(){
            this.myInt = 0;
        }
        public IntHolder(int myInt){
            this.myInt = myInt;
        }

        public int getMyInt() {
            return myInt;
        }

        public void setMyInt(int myInt) {
            this.myInt = myInt;
        }
    }

    void backtracking(LinkedList<Utilizator> lantUtilizatori, IntHolder lungimeMax, LinkedList<Utilizator> comunitate){
        // genereaza toate lanturile posibile intr-o comunitate
        lantUtilizatori.add(new Utilizator("",""));

        for(Utilizator utilizator : comunitate){
                lantUtilizatori.set(lantUtilizatori.size()-1, utilizator);

                if(valid(lantUtilizatori)) {
                    LinkedList<Utilizator> lantUtilizatoriCopie = (LinkedList<Utilizator>)lantUtilizatori.clone();

                    if (lantUtilizatori.size()-1 > lungimeMax.getMyInt())
                        lungimeMax.setMyInt(lantUtilizatori.size()-1);
                    if(lantUtilizatori.size() < comunitate.size())
                        backtracking(lantUtilizatoriCopie, lungimeMax, comunitate);
                }
            }
    }

    public LinkedList<Utilizator> ceaMaiSociabilaComunitate(){
        int lungimeMaxima=0;
        LinkedList<Utilizator> componentaMaxima = new LinkedList<>();
        LinkedList<LinkedList<Utilizator>> comunitati = this.comunitati();

        for(LinkedList<Utilizator> comunitate : comunitati) {
            IntHolder lungimeMaximaLocala = new IntHolder();
            LinkedList<Utilizator> lantUtilizatori = new LinkedList<>();

            this.backtracking(lantUtilizatori, lungimeMaximaLocala, comunitate);
            //System.out.println(lungimeMaximaLocala.getMyInt());

            if(lungimeMaximaLocala.getMyInt() > lungimeMaxima) {
                lungimeMaxima = lungimeMaximaLocala.getMyInt();
                componentaMaxima = comunitate;
            }
        }
        return componentaMaxima;
    }

    public Prietenie getFriendshipByUsers(Utilizator utilizator1, Utilizator utilizator2){
        Optional<Prietenie> prietenie = repoPrietenie.findOne(new Tuple<>(utilizator1.getId(),utilizator2.getId()));
        if(!prietenie.isPresent())
         return repoPrietenie.findOne(new Tuple<>(utilizator2.getId(), utilizator1.getId())).get();
        else
            return prietenie.get();
    }

}
