package com.example.registration.service;


import com.example.registration.model.User;
import com.example.registration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;


@Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

}
    public User findOne(int id) {
        Optional<User> foundPerson = userRepository.findById(id);
        return foundPerson.orElse(null);
    }


    public List<User> findAll() {
        List<User> foundPerson = userRepository.findAll();
        return foundPerson;
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> foundPerson = userRepository.findByEmail(email);
        return Optional.ofNullable(foundPerson.orElse(null));
    }
    @Transactional
    public void save(User user) {

        userRepository.save(user);
    }

    @Transactional
    public void update(int id, User updatedUser) {
        updatedUser.setId(id);
        userRepository.save(updatedUser);
    }
}
