package ru.guap.shoppinglist.controller;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class EntityValidationException extends RuntimeException {

    public <T> EntityValidationException(Set<ConstraintViolation<T>> constraints) {
        super(constraints.toString());
    }
}
