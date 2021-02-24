package socialnetwork.domain.validators;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.exceptions.ValidationException;

public class PrietenieValidator implements Validator<Prietenie>{

    @Override
    public void validate(Prietenie entity) throws ValidationException {
        String validari = "";

        if(entity.getID1() == null)
            validari += "Primul ID nu poate fi vid!\n";

        if(entity.getID2() == null)
            validari += "Ultimul ID nu poate fi vid!\n";

        if(!validari.isEmpty())
            throw new ValidationException(validari);
    }
}
