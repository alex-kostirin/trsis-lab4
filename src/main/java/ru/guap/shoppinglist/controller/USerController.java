package ru.guap.shoppinglist.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class USerController {
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
