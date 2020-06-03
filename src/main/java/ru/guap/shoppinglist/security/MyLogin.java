package ru.guap.shoppinglist.security;

import org.springframework.security.core.authority.AuthorityUtils;
import ru.guap.shoppinglist.model.User;

public class MyLogin extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    public MyLogin(User user) {
        super(user.getLogin(), user.getPassHash(), AuthorityUtils.createAuthorityList("ALL"));
    }

}
