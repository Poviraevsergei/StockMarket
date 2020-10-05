package by.itechart.controller;

import by.itechart.model.domain.User;
import by.itechart.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findAll")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/create")
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @PutMapping("/update")
    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}