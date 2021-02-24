package app.repository.file;

import app.domain.Client;

import java.util.List;

public class ClientFile extends AbstractFileRepository<Long, Client>{
    public ClientFile(String fileName) {
        super(fileName);
    }

    @Override
    public Client extractEntity(List<String> attributes) {
        Client client = new Client(Long.parseLong(attributes.get(0)),attributes.get(1), Integer.parseInt(attributes.get(2)),Integer.parseInt(attributes.get(3)), Client.hobbies.valueOf(attributes.get(4)));
        client.setId(Long.parseLong(attributes.get(0)));
        return client;
    }

    @Override
    protected String createEntityAsString(Client entity) {
        return entity.getClientId() + ";" + entity.getName() + ";" + entity.getFidelityGrade() + ";" + entity.getVarsta() + ";" + entity.getHobby();
    }
}
