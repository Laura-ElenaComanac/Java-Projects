package utils;

import model.Trip;
import service.AttractionException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
    void bookedTrip(Iterable<Trip> trips) throws AttractionException, RemoteException;
}
