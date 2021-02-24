package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Prietenie extends Entity<Tuple<Long,Long>>{

    private Long ID1;
    private Long ID2;
    private LocalDateTime date;

    public Prietenie(Long ID1, Long ID2, LocalDateTime date){
        this.ID1 = ID1;
        this.ID2 = ID2;
        this.date = date;
    }
    /**
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate(){
        return date;
    }

    public Long getID1(){
        return ID1;
    }

    public Long getID2(){
        return ID2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID1(), getID2());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Prietenie))
            return false;
        Prietenie prieten = (Prietenie) obj;
        return getID1().equals(prieten.getID1()) && getID2().equals(prieten.getID2()) ||
                getID1().equals(prieten.getID2()) && getID2().equals(prieten.getID1());
    }

    @Override
    public String toString() {
        return "Prietenie{"+
                "ID1='" + ID1 + '\'' +
                ", ID2='" + ID2 + '\'' +
                ", date=" + date.toString().replace('T',' ') +
                '}';
    }
}