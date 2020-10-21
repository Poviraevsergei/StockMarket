package by.itechart.service.impl;

import by.itechart.model.domain.Status;
import by.itechart.model.domain.Security;
import by.itechart.model.domain.User;
import by.itechart.model.response.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import by.itechart.service.UserService;
import by.itechart.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        Security security = new Security();
        security.setLogin(createUserRequest.getLogin());
        security.setPassword(createUserRequest.getPassword());
        security.setUserRole("ROLE_USER");
        Status status = new Status();
        status.setIsActive(false);
        status.setExpiryDate(Timestamp.valueOf(LocalDateTime.now()));
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setStatus(status);
        user.setSecurity(security);
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