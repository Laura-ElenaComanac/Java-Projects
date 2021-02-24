package socialnetwork.domain.validators;

import socialnetwork.domain.Message;
import socialnetwork.domain.exceptions.ValidationException;

public class MessageValidator implements Validator<Message> {
    @Override
    public void validate(Message entity) throws ValidationException {
        String validari = "";

        if(entity.getId() == null)
            validari += "ID-ul nu poate fi vid!\n";

        if(entity.getMessage() == null)
            validari += "Mesajul nu poate fi vid!\n";

        if(entity.getData() == null)
            validari += "Data nu poate fi vida!\n";

        if(!validari.isEmpty())
            throw new ValidationException(validari);
    }
}
