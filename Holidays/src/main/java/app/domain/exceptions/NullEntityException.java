package app.domain.exceptions;

public class NullEntityException extends ValidationException {
    public NullEntityException(String mesaj){
       super(mesaj);
    }
}
