package by.itechart.service.impl;

import by.itechart.exception.CustomException;
import by.itechart.model.domain.Security;
import by.itechart.model.domain.Status;
import by.itechart.model.domain.User;
import by.itechart.model.response.CreateUserRequest;
import by.itechart.repository.UserRepository;
import by.itechart.security.repository.SecurityRepository;
import by.itechart.service.CompanyService;
import by.itechart.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static by.itechart.utils.ProjectProperties.CHANGE_TO_PREMIUM_EXCEPTION;
import static by.itechart.utils.ProjectProperties.ROLE_CLIENT;
import static by.itechart.utils.ProjectProperties.ROLE_USER;
import static by.itechart.utils.ProjectProperties.ADD_COMPANY_TO_ACCOUNT_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;
    private final CompanyService companyService;

    @Override
    public Optional<List<User>> findAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        return Optional.of(users);
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
    public Optional<User> changeToPremium(String userLogin) {
        User user = userRepository.findUserById(securityRepository.findByLogin(userLogin).getId())
                .orElseThrow(() -> new CustomException(CHANGE_TO_PREMIUM_EXCEPTION));
        user.getStatus().setIsActive(true);
        user.getStatus().setExpiryDate(LocalDate.now().plusYears(1));
        user.getSecurity().setUserRole(ROLE_CLIENT);
        userRepository.save(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> addCompanyToAccount(String ticker) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserById(securityRepository.findByLogin(userDetails.getUsername()).getId())
                .orElseThrow(() -> new CustomException(ADD_COMPANY_TO_ACCOUNT_EXCEPTION));
        if (user.getUserCompanies().size() >= 3 && user.getSecurity().getUserRole().equals(ROLE_USER)) {
            return Optional.of(user);
        }
        user.getUserCompanies().add(companyService.findCompanyFromDbByTicker(ticker)
                .orElseThrow(() -> new CustomException(ADD_COMPANY_TO_ACCOUNT_EXCEPTION)));
        userRepository.save(user);

        return Optional.of(user);
    }

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        Security security = new Security();
        security.setLogin(createUserRequest.getLogin());
        security.setPassword(createUserRequest.getPassword());
        security.setUserRole(ROLE_USER);
        Status status = new Status();
        status.setIsActive(false);
        status.setExpiryDate(LocalDate.now());
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setChanged(LocalDate.now());
        user.setCreated(LocalDate.now());
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