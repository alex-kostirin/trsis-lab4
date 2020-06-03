package ru.guap.shoppinglist.controller;

import org.springframework.stereotype.Component;

import javax.validation.Validator;


@Component
public class EntityValidator<T> {
    private final Validator validator;
    public EntityValidator(Validator validator) {
        this.validator = validator;
    }

    public final void validate(T v) {
        var constraints =   validator.validate(v);
        if (!constraints.isEmpty()) {
            throw new EntityValidationException(constraints);
        }
    }
}
