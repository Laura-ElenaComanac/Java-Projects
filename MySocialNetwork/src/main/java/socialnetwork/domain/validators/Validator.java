package socialnetwork.domain.validators;

import socialnetwork.domain.exceptions.ValidationException;

public interface Validator<T>{
    void validate(T entity)
        throws ValidationException;
}