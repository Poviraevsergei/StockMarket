package by.itechart.service;

import by.itechart.model.domain.User;
import by.itechart.model.response.CreateUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<List<User>> findAllUsers();

    Optional<User> findUserById(Long id);

    Optional<User> findUserByEmail(String email);

    User createUser(CreateUserRequest CreateUserRequest);

    User updateUser(User user);

    void deleteUserById(Long id);
}