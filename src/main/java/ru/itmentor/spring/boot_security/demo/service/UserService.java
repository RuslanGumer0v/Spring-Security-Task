package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;
import ru.itmentor.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addUser(User user) {
        if (user.getId() != null && this.userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User with this ID already exists. Use updateUser instead.");
        }
        this.userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateUser(User user) {
        if (user.getId() == null || !this.userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User does not exist. Use addUser instead.");
        }
        this.userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
