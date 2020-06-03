package ru.guap.shoppinglist.fixture;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.guap.shoppinglist.model.User;
import ru.guap.shoppinglist.repository.UserRepository;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new User("admin", "password")));
            log.info("Preloading " + repository.save(new User("user", "password")));
        };
    }
}
