package by.itechart.controller;

import by.itechart.exception.CustomException;
import by.itechart.model.domain.User;
import by.itechart.model.response.CreateUserRequest;
import by.itechart.service.UserService;
import by.itechart.utils.SendMailMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static by.itechart.utils.ProjectProperties.CHANGE_TO_PREMIUM_EXCEPTION;
import static by.itechart.utils.ProjectProperties.USER_WAS_NOT_REGISTERED;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    private final UserService userService;
    private final SendMailMethods sendMail;

    @PostMapping(value = "/registration")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);
        if (user == null) {
            throw new CustomException(USER_WAS_NOT_REGISTERED);
        }
        sendMail.sendNewPasswordMailToUser(user, createUserRequest.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/changeToPremium")
    public ResponseEntity<User> changeToPremium(String userLogin) {
        User user = userService.changeToPremium(userLogin).orElseThrow(() -> new CustomException(CHANGE_TO_PREMIUM_EXCEPTION));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}