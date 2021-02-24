package app.service;

import app.domain.Client;
import app.repository.Repository;
import app.utils.events.ChangeEventType;
import app.utils.events.ClientChangeEvent;

public class ClientService extends Service<Long, Client, ClientChangeEvent>{
    public ClientService(Repository<Long, Client> repo) {
        super(repo);
    }

    @Override
    protected ClientChangeEvent createNewEvent(ChangeEventType type, Client client) {
        return new ClientChangeEvent(type,client);
    }


}
