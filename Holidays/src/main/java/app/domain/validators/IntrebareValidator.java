package app.domain.validators;

import app.domain.Hotel;
import app.domain.exceptions.ValidationException;

public class IntrebareValidator implements Validator<Hotel>{
    @Override
    public void validate(Hotel entity) throws ValidationException {
        String validari = "";

//        if(entity.getDescriere().equals(""))
//            validari += "Descrierea intrebarii cu ID-ul " + entity.getId() + " nu poate fi vida!\n";
//
//        if(entity.getPunctaj() <= 0)
//            validari += "Punctajul intrebarii cu ID-ul " + entity.getId() + " nu poate fi negativ!\n";
//

        if(!validari.isEmpty())
            throw new ValidationException(validari);
    }
}