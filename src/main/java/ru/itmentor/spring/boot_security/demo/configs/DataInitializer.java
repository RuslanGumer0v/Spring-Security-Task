package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder; // Инжектируем PasswordEncoder

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Создаем роли, если их еще нет
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
//        Role userRole = roleRepository.findByName("ROLE_USER")
//                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        // Создаем пользователя Admin
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setFirstName("Ruslan");
            admin.setLastName("Gumerov");
            admin.setAge(34);
            admin.setEmail("ruslan_gumerov@mail.ru");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        }
        // Создаем пользователя User
//        if (userRepository.findByUsername("user").isEmpty()) {
//            User user = new User();
//            user.setUsername("user");
//            user.setFirstName("user");
//            user.setLastName("user");
//            user.setAge(18);
//            user.setEmail("user@mail.ru");
//            user.setPassword(passwordEncoder.encode("user"));
//            user.setRoles(Set.of(userRole));
//            userRepository.save(user);
//        }
    }
}
