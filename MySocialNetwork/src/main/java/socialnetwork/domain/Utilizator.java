package socialnetwork.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<Long>{
    private String firstName;
    private String lastName;
    private List<Utilizator> friends;

    public Utilizator(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        friends = new LinkedList<>();
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public List<Utilizator> getFriends(){
        return friends;
    }

    public void setFriends(List<Utilizator> friends) {
        this.friends = friends;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFriends());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Utilizator))
            return false;
        Utilizator util = (Utilizator) obj;
        return getFirstName().equals(util.getFirstName()) &&
                getLastName().equals(util.getLastName()); //&&
                //getFriends().equals(util.getFriends());
    }

//    @Override
//    public String toString() {
//        StringBuilder friendsString = new StringBuilder();
//
//        for(Utilizator friend : friends)
//            friendsString.append(friend.firstName).append(" ").append(friend.lastName).append(";");
//
//        return "Utilizator{"+ "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' +
//                ", friends=" + friendsString.toString() + '}';
//    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long aLong) {
        super.setId(aLong);
    }
}