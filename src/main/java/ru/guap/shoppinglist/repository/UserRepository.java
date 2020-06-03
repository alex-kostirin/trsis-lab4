package ru.guap.shoppinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guap.shoppinglist.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}


