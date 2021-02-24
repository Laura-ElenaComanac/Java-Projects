package socialnetwork.domain;

import java.util.Comparator;

public class DataComparator implements Comparator<Message> {
    @Override
    public int compare(Message m1, Message m2) {
        return m1.getData().toString().compareTo(m2.getData().toString());
    }
}
