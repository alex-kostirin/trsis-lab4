package ru.guap.shoppinglist.controller;

import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class MainAdvice {

    private final String NOT_FOUND_TITLE = "not found";
    private final String VALIDATION_ERROR_TITLE = "validation error";

    @ResponseBody
    @ExceptionHandler(ListNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Problem listNotFoundHandler(ListNotFoundException ex) {
        return Problem.create().withTitle(NOT_FOUND_TITLE).withDetail(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Problem itemNotFoundHandler(ItemNotFoundException ex) {
        return Problem.create().withTitle(NOT_FOUND_TITLE).withDetail(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(EntityValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Problem entityValidationHandler(EntityValidationException ex) {
        return Problem.create().withTitle(VALIDATION_ERROR_TITLE).withDetail(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Problem methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
        return Problem.create().withTitle(VALIDATION_ERROR_TITLE).withDetail(ex.getMessage());
    }
}