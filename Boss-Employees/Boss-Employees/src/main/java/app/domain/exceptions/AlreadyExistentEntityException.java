package app.domain.exceptions;

public class AlreadyExistentEntityException extends ValidationException {
    public AlreadyExistentEntityException(String mesaj) {
        super(mesaj);
    }
}
