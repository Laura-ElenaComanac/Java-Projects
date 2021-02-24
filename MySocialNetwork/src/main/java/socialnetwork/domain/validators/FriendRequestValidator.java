package socialnetwork.domain.validators;

import socialnetwork.domain.FriendRequest;
import socialnetwork.domain.exceptions.ValidationException;

public class FriendRequestValidator implements Validator<FriendRequest> {
    @Override
    public void validate(FriendRequest entity) throws ValidationException {
        String validari = "";

        if(entity.getFrom() == null)
            validari += "Expeditorul nu poate fi vid!\n";

        if(entity.getTo() == null)
            validari += "Destinatarul nu poate fi vid!\n";

        if(entity.getStatus() == null)
            validari += "Statusul nu poate fi vid!\n";

        if(entity.getData() == null)
            validari += "Data nu poate fi vida!\n";

        if(!validari.isEmpty())
            throw new ValidationException(validari);
    }
}
