package socialnetwork.domain.validators;

import socialnetwork.domain.Recipient;
import socialnetwork.domain.exceptions.ValidationException;

public class RecipientValidator implements Validator<Recipient> {
    @Override
    public void validate(Recipient entity) throws ValidationException {
        String validari = "";

        if(entity.getId1() == null)
            validari += "Primul ID nu poate fi vid!\n";

        if(entity.getId2() == null)
            validari += "Al doilea ID nu poate fi vid!\n";

        if(entity.getId3() == null)
            validari += "Al treilea ID nu poate fi vid!\n";

        if(!validari.isEmpty())
            throw new ValidationException(validari);
    }
}
