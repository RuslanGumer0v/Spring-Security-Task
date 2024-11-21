package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.Set;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // Страница регистрации
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(User user) {
        // Пример регистрации с ролью USER
        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        return "redirect:/login"; // Перенаправление на страницу логина
    }
}
