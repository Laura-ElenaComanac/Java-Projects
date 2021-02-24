package socialnetwork.domain;

import java.util.Objects;

public class Recipient extends Entity<Tuple<Long, Tuple<Long, Long>>> {
    private Long id1;
    private Long id2;
    private Long id3;

    public Recipient(Long id1, Long id2, Long id3){
        this.id1 = id1;
        this.id2 = id2;
        this.id3 = id3;
    }

    public Long getId1() {
        return id1;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    public Long getId3() {
        return id3;
    }

    public void setId3(Long id3) {
        this.id3 = id3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId1(), getId2(), getId3());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Recipient))
            return false;
        Recipient rec = (Recipient) obj;
        return getId1().equals(rec.getId1()) &&
                getId2().equals(rec.getId2()) &&
                getId3().equals(rec.getId3());
    }

    @Override
    public String toString() {
        return "Recipient{"+ "Id1='" + id1 + '\'' + ", Id2='" + id2 + '\'' +
                ", Id3=" + id3 + '}';
    }
}
