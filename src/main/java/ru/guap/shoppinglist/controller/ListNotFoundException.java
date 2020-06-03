package ru.guap.shoppinglist.controller;

public class ListNotFoundException extends RuntimeException {
    public ListNotFoundException(Integer id) {
        super("Could not find list " + id);
    }
}
