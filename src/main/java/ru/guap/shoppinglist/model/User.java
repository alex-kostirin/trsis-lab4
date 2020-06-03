package ru.guap.shoppinglist.model;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@NoArgsConstructor
@Entity(name = "users")
public class User {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
    private @Column(nullable = false, unique = true) String login;
    private @Column(name = "pass_hash", nullable = false) String passHash;

    public User(String login, String password) {
        var encoder = new BCryptPasswordEncoder();
        this.login = login;
        this.passHash = encoder.encode(password);
    }
}
