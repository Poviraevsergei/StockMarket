package by.itechart.controller;

import by.itechart.model.domain.User;
import lombok.RequiredArgsConstructor;
import by.itechart.service.UserService;
import by.itechart.utils.SendMailMethods;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import by.itechart.exception.CustomException;
import org.springframework.http.ResponseEntity;
import by.itechart.model.response.CreateUserRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static by.itechart.utils.ProjectProperties.USER_WAS_NOT_REGISTERED;
import static by.itechart.utils.ProjectProperties.CHANGE_TO_PREMIUM_EXCEPTION;
import static by.itechart.utils.ProjectProperties.CHANGE_PASSWORD_EXCEPTION;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    private final UserService userService;
    private final SendMailMethods sendMail;

    @GetMapping
    public ResponseEntity<String> welcomePage(){
        return new ResponseEntity<>("Nice to see you! Welcome to Stock Market site!", HttpStatus.OK);
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);
        if (user == null) {
            throw new CustomException(USER_WAS_NOT_REGISTERED);
        }
        sendMail.sendNewPasswordMailToUser(user, createUserRequest.getPassword());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(value = "/changeToPremium")
    public ResponseEntity<User> changeToPremium(String userLogin) {
        User user = userService.changeToPremium(userLogin).orElseThrow(() -> new CustomException(CHANGE_TO_PREMIUM_EXCEPTION));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/changePassword")
    public ResponseEntity<User> changePassword(String newPassword) {
        User user = userService.changePassword(newPassword).orElseThrow(() -> new CustomException(CHANGE_PASSWORD_EXCEPTION));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
