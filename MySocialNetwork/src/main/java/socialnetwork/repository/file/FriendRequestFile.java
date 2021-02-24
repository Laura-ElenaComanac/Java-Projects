package socialnetwork.repository.file;

import socialnetwork.domain.FriendRequest;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.FriendRequestValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FriendRequestFile extends AbstractFileRepository<Tuple<Long,Long>, FriendRequest> {
    public FriendRequestFile(String fileName, FriendRequestValidator validator){
        super(fileName, validator);
    }

    @Override
    public FriendRequest extractEntity(List<String> attributes) {
        Long from = Long.parseLong(attributes.get(0));
        Long to = Long.parseLong(attributes.get(1));
        String status = attributes.get(2);

        String date = attributes.get(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        FriendRequest friendRequest = new FriendRequest(from, to, status, dateTime);
        Tuple<Long,Long> tuple = new Tuple<>(from, to);
        friendRequest.setId(tuple);
        return friendRequest;
    }

    @Override
    protected String createEntityAsString(FriendRequest friendRequest) {
        return friendRequest.getFrom()+";"+friendRequest.getTo()+";"+friendRequest.getStatus()+";"+friendRequest.getData().toString().replace('T',' ');
    }
}
