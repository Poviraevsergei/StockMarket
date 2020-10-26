package by.itechart.controller;

import by.itechart.exception.CustomException;
import by.itechart.exception.ResourceNotFoundException;
import by.itechart.model.domain.User;
import by.itechart.model.response.CreateUserRequest;
import by.itechart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static by.itechart.utils.ProjectProperties.USERS_NOT_FOUND;
import static by.itechart.utils.ProjectProperties.USER_NOT_FOUND;
import static by.itechart.utils.ProjectProperties.USER_WAS_NOT_CREATED;
import static by.itechart.utils.ProjectProperties.USER_WAS_NOT_UPDATED;
import static by.itechart.utils.ProjectProperties.ADD_COMPANY_TO_ACCOUNT_EXCEPTION;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> allUsers = userService.findAllUsers().orElseThrow(() -> new ResourceNotFoundException(USERS_NOT_FOUND));
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/addCompany")
    public ResponseEntity<User> addCompanyToAccount(String ticker) {
        User user = userService.addCompanyToAccount(ticker).orElseThrow(() -> new ResourceNotFoundException(ADD_COMPANY_TO_ACCOUNT_EXCEPTION));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);
        if (user == null) {
            throw new CustomException(USER_WAS_NOT_CREATED);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User resultUser = userService.updateUser(user);
        if (resultUser == null) {
            throw new CustomException(USER_WAS_NOT_UPDATED);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}