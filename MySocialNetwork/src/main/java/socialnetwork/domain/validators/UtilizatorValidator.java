package socialnetwork.domain.validators;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.exceptions.ValidationException;

public class UtilizatorValidator implements Validator<Utilizator>{
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String validari = "";

        if(entity.getFirstName().equals(""))
            validari += "Primul nume al utilizatorului cu ID-ul " + entity.getId() + " nu poate fi vid!\n";

        if(entity.getLastName().equals(""))
            validari += "Ultimul nume al utilizatorului cu ID-ul " + entity.getId() + " nu poate fi vid!\n";


        if(!validari.isEmpty())
            throw new ValidationException(validari);
    }
}