package by.itechart.service.impl;

import by.itechart.model.domain.Role;
import by.itechart.model.domain.User;
import lombok.RequiredArgsConstructor;
import by.itechart.service.UserService;
import by.itechart.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        Role role = new Role();
        role.setIsActive(false);
        role.setExpiryDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}