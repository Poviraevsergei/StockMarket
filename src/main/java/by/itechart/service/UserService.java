package by.itechart.service;

import by.itechart.model.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers();

    Optional<User> findUserById(Long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUserById(Long id);
}