package ru.guap.shoppinglist.controller;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Integer id) {
        super("Could not find item " + id);
    }
}