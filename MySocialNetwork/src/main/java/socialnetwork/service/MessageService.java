package socialnetwork.service;

import socialnetwork.domain.*;
import socialnetwork.domain.exceptions.NullEntityException;
import socialnetwork.repository.Repository;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observable;
import socialnetwork.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessageService implements Observable<UtilizatorChangeEvent> {
    private Repository<Long, Utilizator> repoUtilizator;
    private Repository<Long, Message> repoMessage;
    private Repository<Tuple<Long, Tuple<Long,Long>>, Recipient> repoRecipient;

    public MessageService(Repository<Long, Utilizator> repoUtilizator, Repository<Long, Message> repoMessage, Repository<Tuple<Long, Tuple<Long,Long>>, Recipient> repoRecipient){
        this.repoUtilizator = repoUtilizator;
        this.repoMessage = repoMessage;
        this.repoRecipient = repoRecipient;

        Utilizator utilizator1, utilizator2;
        Message message;

        for(Recipient recipient : repoRecipient.findAll()) {
            message = repoMessage.findOne(recipient.getId1()).get();
            utilizator1 = repoUtilizator.findOne(recipient.getId2()).get();
            utilizator2 = repoUtilizator.findOne(recipient.getId3()).get();

            if(message.getTo() == null){
                List<Utilizator> list = new LinkedList<>();
                list.add(utilizator2);
                message.setTo(list);
            }
            else{
                message.getTo().add(utilizator2);
            }

            message.setFrom(utilizator1);
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

    public List<Message> getAllMessages(){
        Iterable<Message> messages = repoMessage.findAll();
        return StreamSupport.stream(messages.spliterator(), false).collect(Collectors.toList());
    }

    public List<Recipient> getAllRecipients(){
        Iterable<Recipient> recipients = repoRecipient.findAll();
        return StreamSupport.stream(recipients.spliterator(), false).collect(Collectors.toList());
    }

    public List<Message> getUsersMessages(Utilizator utilizator1, Utilizator utilizator2){
        for(Utilizator util : repoUtilizator.findAll()) {
            if (util.equals(utilizator1))
                utilizator1.setId(util.getId());
            if (util.equals(utilizator2))
                utilizator2.setId(util.getId());
        }

        List<Message> messageList = new LinkedList<>();
        for(Message message : repoMessage.findAll()) {
            if (message.getFrom().equals(utilizator1) && message.getTo().contains(utilizator2)
                    || message.getFrom().equals(utilizator2) && message.getTo().contains(utilizator1))
                messageList.add(message);
        }
        DataComparator comparator = new DataComparator();
        messageList.sort(comparator);

        return messageList;
    }

    public List<Message> getMessagesByUser(Utilizator utilizator){
        for(Utilizator util : repoUtilizator.findAll()) {
            if (util.equals(utilizator))
                utilizator.setId(util.getId());
        }

        List<Message> messageList = new LinkedList<>();
        for(Message message : repoMessage.findAll()) {
            if (message.getFrom().equals(utilizator) || message.getTo().contains(utilizator))
                messageList.add(message);
        }
        DataComparator comparator = new DataComparator();
        messageList.sort(comparator);

        return messageList;
    }

    public void sendMessage(Utilizator expeditor, List<Utilizator> destinatari, String mesaj, LocalDateTime dateTime){
        List<Utilizator> dest = new LinkedList<>();
        Utilizator exp = null;
        boolean test1 = false;
        boolean test2 = false;
        for(Utilizator util : repoUtilizator.findAll()) {
            if (util.equals(expeditor)) {
                exp = util;
                test1=true;
            }
            else
                for(Utilizator utilizator : destinatari) {
                    if (utilizator.equals(util)) {
                        dest.add(util);
                        test2 = true;
                    }
                }
        }

        if(test1 == false || test2 == false)
            throw new NullEntityException("Utilizataor inexistent in lista de utilizatori ai aplicatiei!");

        Long idMaxMessage = StreamSupport.stream(this.repoMessage.findAll().spliterator(),false).map(Message::getId).max(Long::compareTo).get();
        if(idMaxMessage == null)
            idMaxMessage = 1L;
        else
            idMaxMessage++;
        Message message = new Message(idMaxMessage,exp,dest,mesaj,dateTime);
        repoMessage.save(message);

        Recipient recipient;
        for(Utilizator utilizator : dest) {
            recipient = new Recipient(idMaxMessage, exp.getId(), utilizator.getId());
            recipient.setId(new Tuple<>(recipient.getId1(),new Tuple<>(recipient.getId2(),recipient.getId3())));
            repoRecipient.save(recipient);
        }
    }

    public void replyMessage(Long idMesaj, Utilizator utilizator, String mesaj, LocalDateTime dateTime){
        for(Utilizator util : repoUtilizator.findAll()) {
            if (util.equals(utilizator))
                utilizator.setId(util.getId());
        }

        Long idMaxMessage = StreamSupport.stream(this.repoMessage.findAll().spliterator(),false).map(Message::getId).max(Long::compareTo).get();
        if(idMaxMessage == null)
            idMaxMessage = 1L;
        else
            idMaxMessage++;

        boolean test = false;
        for(Recipient rec : repoRecipient.findAll())
            if(rec.getId1().equals(idMesaj))
                if(rec.getId3().equals(utilizator.getId()) || rec.getId2().equals(utilizator.getId())){
                    test = true;
                }
        if(!test)
            throw new NullEntityException("Utilizatorul nu exista in conversatie!");

        Optional<Message> message = repoMessage.findOne(idMesaj);
        List<Utilizator> to = new LinkedList<>();
        to.add(message.get().getFrom());

        ReplyMessage replyMessage = new ReplyMessage(idMaxMessage, utilizator, to, mesaj, dateTime, message.get());
        repoMessage.save(replyMessage);

        Recipient recipient;
        recipient = new Recipient(idMaxMessage, utilizator.getId(), message.get().getFrom().getId());
        recipient.setId(new Tuple<>(recipient.getId1(),new Tuple<>(recipient.getId2(),recipient.getId3())));
        repoRecipient.save(recipient);
    }

    public void replyAllMessage(Long idMesaj, Utilizator utilizator, String mesaj, LocalDateTime dateTime){
        for(Utilizator util : repoUtilizator.findAll()) {
            if (util.equals(utilizator))
                utilizator.setId(util.getId());
        }

        boolean test = false;
        for(Recipient rec : repoRecipient.findAll()) {
            if (rec.getId1().equals(idMesaj))
                if (rec.getId3().equals(utilizator.getId()) || rec.getId2().equals(utilizator.getId()))
                    test = true;
        }
        if(test == false)
                throw new NullEntityException("Utilizatorul nu exista in conversatie!");

        Long idMaxMessage = StreamSupport.stream(this.repoMessage.findAll().spliterator(),false).map(Message::getId).max(Long::compareTo).get();
        if(idMaxMessage == null)
            idMaxMessage = 1L;
        else
            idMaxMessage++;

        Optional<Message> message = repoMessage.findOne(idMesaj);

        List<Utilizator> to = message.get().getTo();
        to.remove(utilizator);
        to.add(message.get().getFrom());

        ReplyMessage replyMessage = new ReplyMessage(idMaxMessage, utilizator, to, mesaj, dateTime, message.get());
        repoMessage.save(replyMessage);

        Recipient recipient;
        for(Utilizator utili : to) {
            recipient = new Recipient(idMaxMessage, utilizator.getId(), utili.getId());
            recipient.setId(new Tuple<>(recipient.getId1(), new Tuple<>(recipient.getId2(), recipient.getId3())));
            repoRecipient.save(recipient);
        }
    }
}
