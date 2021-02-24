package socialnetwork.repository.file;

import socialnetwork.domain.Message;
import socialnetwork.domain.ReplyMessage;
import socialnetwork.domain.validators.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class MessageFile extends AbstractFileRepository<Long, Message>{
    public MessageFile(String fileName, Validator<Message> validator){
        super(fileName, validator);
    }

    @Override
    public Message extractEntity(List<String> attributes) {
        Long ID = Long.parseLong(attributes.get(0));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(attributes.get(2), formatter);

        Message mesaj;
        if(attributes.get(3).equals("0"))
            mesaj = new Message(ID, null, new LinkedList<>(), attributes.get(1), dateTime);
        else
            mesaj = new ReplyMessage(ID, null, new LinkedList<>(), attributes.get(1), dateTime,findOne(Long.parseLong(attributes.get(3))).get());
        return mesaj;
    }

    @Override
    protected String createEntityAsString(Message entity) {
        return entity.getId()+";"+entity.getMessage()+";"+entity.getData().toString().replace('T', ' ')+";"+ entity.getOriginalMessage();
    }
}
