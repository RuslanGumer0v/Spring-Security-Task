package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;


@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String getUserDetails(Model model, Authentication authentication) {
        model.addAttribute("user", userService.getUserByUserName(authentication.getName()));
        return "user";
    }

    @GetMapping("/user/edit")
    public String showEditPasswordForm(Model model, Authentication authentication) {
        model.addAttribute("user", userService.getUserByUserName(authentication.getName()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "editPassword";
    }

    @PostMapping("/user/edit")
    public String updatePassword(@RequestParam("password") String newPassword, Authentication authentication) {
        User user = userService.getUserByUserName(authentication.getName());
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(user);
        return "redirect:/user?success"; // Возвращаемся на страницу пользователя с успешным сообщением
    }
}

