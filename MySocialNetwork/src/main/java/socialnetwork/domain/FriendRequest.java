package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class FriendRequest extends Entity<Tuple<Long,Long>> {

    private Long from;
    private Long to;
    private String status;
    private LocalDateTime data;

    public FriendRequest(Long from, Long to, String status, LocalDateTime data) {
        this.from = from;
        this.to = to;
        this.status = status;
        this.data = data;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrom(), getTo());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof FriendRequest))
            return false;
        FriendRequest fr = (FriendRequest) obj;
        return getFrom().equals(fr.getFrom()) && getTo().equals(fr.getTo());
    }

    @Override
    public String toString() {
        return "CererePrietenie{"+ "from='" + from + '\'' + ", to='" + to + '\'' + ", status='" + status + ", data='" + data.toString().replace('T', ' ') +'}';
    }
}
