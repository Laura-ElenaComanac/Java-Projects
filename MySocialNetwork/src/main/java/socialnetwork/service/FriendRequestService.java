package socialnetwork.service;

import socialnetwork.domain.FriendRequest;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.exceptions.AlreadyExistentEntityException;
import socialnetwork.domain.exceptions.NullEntityException;
import socialnetwork.repository.Repository;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observable;
import socialnetwork.utils.observer.Observer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FriendRequestService implements Observable<UtilizatorChangeEvent> {

    private final Repository<Tuple<Long,Long>, FriendRequest> repoFriendRequest;
    private final Repository<Long, Utilizator> repoUtilizator;
    private PrietenieService servicePrietenie;

    public FriendRequestService(Repository<Tuple<Long,Long>, FriendRequest> repoFriendRequest, Repository<Long, Utilizator> repoUtilizator, PrietenieService servicePrietenie){
        this.repoFriendRequest = repoFriendRequest;
        this.repoUtilizator = repoUtilizator;
        this.servicePrietenie = servicePrietenie;
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

    public List<FriendRequest> getAllFriendRequests(){
        Iterable<FriendRequest> messages = repoFriendRequest.findAll();
        return StreamSupport.stream(messages.spliterator(), false).collect(Collectors.toList());
    }

    public void sendFriendRequest(Utilizator utilizator1, Utilizator utilizator2, LocalDateTime dateTime){
        boolean test1 = false;
        boolean test2 = false;
        for(Utilizator util : repoUtilizator.findAll()) {
            if (util.equals(utilizator1)) {
                utilizator1.setId(util.getId());
                test1 = true;
            }
            if (util.equals(utilizator2)) {
                utilizator2.setId(util.getId());
                test2 = true;
            }
        }

        if(!(test1 && test2))
            throw new NullEntityException("Utilizataor inexistent in lista de utilizatori ai aplicatiei!");

        for(FriendRequest request : getAllFriendRequests())
            if(!request.getStatus().equals("rejected"))
                if(request.getFrom().equals(utilizator1.getId()) && request.getTo().equals(utilizator2.getId()) ||
                     request.getFrom().equals(utilizator2.getId()) && request.getTo().equals(utilizator1.getId()))
                        throw new AlreadyExistentEntityException("Cerere de prietenie deja existenta!");

        String status = "pending";
        FriendRequest friendRequest = new FriendRequest(utilizator1.getId(),utilizator2.getId(),status,dateTime);
        repoFriendRequest.save(friendRequest);
    }

    public void retractFriendRequest(Utilizator utilizator1, Utilizator utilizator2)
    {
        Optional<Utilizator> utilizatorNou1 = StreamSupport.stream(repoUtilizator.findAll().spliterator(), false)
                .filter(u->u.equals(utilizator1))
                .findFirst();
        Optional<Utilizator> utilizatorNou2 = StreamSupport.stream(repoUtilizator.findAll().spliterator(), false)
                .filter(u->u.equals(utilizator2))
                .findFirst();

        boolean test = false;
        for(FriendRequest request : getAllFriendRequests())
            if (request.getStatus().equals("pending"))
                if (request.getFrom().equals(utilizatorNou1.get().getId()) && request.getTo().equals(utilizatorNou2.get().getId()) ||
                        request.getFrom().equals(utilizatorNou2.get().getId()) && request.getTo().equals(utilizatorNou1.get().getId())) {
                    repoFriendRequest.delete(new Tuple(utilizatorNou1.get().getId(), utilizatorNou2.get().getId()));
                    test = true;
                }

        if(test == false)
            throw new AlreadyExistentEntityException("Cerere de prietenie deja acceptata/refuzata!");
    }

    public List<Utilizator> receivedFriendRequests(Utilizator utilizator){
        for(Utilizator util : repoUtilizator.findAll()) {
            if (util.equals(utilizator))
                utilizator.setId(util.getId());
        }

        List<Utilizator> cereriUtilizator = new LinkedList<>();

        for(FriendRequest friendRequest : getAllFriendRequests()){
            if(friendRequest.getStatus().equals("pending") && utilizator.getId().equals(friendRequest.getTo()))
                cereriUtilizator.add(repoUtilizator.findOne(friendRequest.getFrom()).get());
        }
        return cereriUtilizator;
    }

    public List<Tuple<Utilizator,Tuple<String,LocalDateTime>>> receivedFriendRequestsComplete(Utilizator utilizator){
        for(Utilizator util : repoUtilizator.findAll()) {
            if (util.equals(utilizator))
                utilizator.setId(util.getId());
        }

        List<Tuple<Utilizator,Tuple<String,LocalDateTime>>> cereriUtilizator = new LinkedList<>();

        for(FriendRequest friendRequest : getAllFriendRequests()){
            if(utilizator.getId().equals(friendRequest.getTo()))
                cereriUtilizator.add(new Tuple(repoUtilizator.findOne(friendRequest.getFrom()).get(), new Tuple(friendRequest.getStatus(), friendRequest.getData())));
        }
        return cereriUtilizator;
    }

    public void acceptFriendRequest(Utilizator from, Utilizator to){
        for(Utilizator util : repoUtilizator.findAll()) {
            if (util.equals(from))
                from.setId(util.getId());
            if (util.equals(to))
                to.setId(util.getId());
        }

        for(FriendRequest friendRequest : getAllFriendRequests()) {
            if (friendRequest.getStatus().equals("pending") && from.getId().equals(friendRequest.getFrom()) && to.getId().equals(friendRequest.getTo())) {
                friendRequest.setStatus("approved");
                repoFriendRequest.update(friendRequest);
            }
        }

        ZoneId zid = ZoneId.of("Europe/Bucharest");
        LocalDateTime dt = LocalDateTime.now(zid);
        String date = dt.toString().replace('T', ' ').substring(0,16);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        servicePrietenie.addPrietenie(from, to, dateTime);
    }

    public void rejectFriendRequest(Utilizator from, Utilizator to){
        for(Utilizator util : repoUtilizator.findAll()) {
            if (util.equals(from))
                from.setId(util.getId());
            if (util.equals(to))
                to.setId(util.getId());
        }

        for(FriendRequest friendRequest : getAllFriendRequests()) {
            if (friendRequest.getStatus().equals("pending") && from.getId().equals(friendRequest.getFrom()) && to.getId().equals(friendRequest.getTo())) {
                friendRequest.setStatus("rejected");
                repoFriendRequest.update(friendRequest);
            }
        }
    }
}
