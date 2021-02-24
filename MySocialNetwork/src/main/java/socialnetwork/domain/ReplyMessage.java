package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message {

    private final Message mesaj;

    public ReplyMessage(Long id, Utilizator from, List<Utilizator> to, String message, LocalDateTime data, Message mesaj){
        super(id, from, to, message, data);
        this.mesaj = mesaj;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof ReplyMessage))
            return false;
        ReplyMessage msg = (ReplyMessage) obj;
        return getId().equals(msg.getId());
    }

    @Override
    public String toString() {
        return mesaj.toString() + "\n" + super.toString()  ;
    }

    @Override
    public Long getOriginalMessage() {
        return this.mesaj.getId();
    }
}
