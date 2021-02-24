package app.utils.observer;

import app.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}