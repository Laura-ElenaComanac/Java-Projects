package app.domain.validators;

import app.domain.exceptions.ValidationException;

public interface Validator<T>{
    void validate(T entity)
        throws ValidationException;
}