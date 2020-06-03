package ru.guap.shoppinglist.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.guap.shoppinglist.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        var login = userRepository.findByLogin(string);
        if (login == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new MyLogin(login);
    }

}
