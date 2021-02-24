package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Message extends Entity<Long> {

    //private Long id;
    private Utilizator from;
    private List<Utilizator> to;
    private String message;
    private LocalDateTime data;

    public Message(Long id, Utilizator from, List<Utilizator> to, String message, LocalDateTime data){
        this.setId(id);
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Message getThis(){
        return this;
    }

    public Utilizator getFrom() {
        return from;
    }

    public void setFrom(Utilizator from) {
        this.from = from;
    }

    public List<Utilizator> getTo() {
        return to;
    }

    public void setTo(List<Utilizator> to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Message))
            return false;
        Message msg = (Message) obj;
        return getId().equals(msg.getId());
    }

//    @Override
//    public String toString() {
//        return "Mesaj{"+ "from='" + from + '\'' + ", to='" + to + '\'' + ", message='" + message + ", data='" + data.toString().replace('T', ' ') +'}';
//    }

    @Override
    public String toString() {
        return from.toString() + ": " + message;
    }

    public Long getOriginalMessage(){
        return 0L;
    }
}
