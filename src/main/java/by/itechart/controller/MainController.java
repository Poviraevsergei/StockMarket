package by.itechart.controller;

import by.itechart.model.domain.User;
import by.itechart.model.response.CreateUserRequest;
import by.itechart.service.UserService;
import by.itechart.utils.SendMailMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {

    private final UserService userService;
    private final SendMailMethods sendMail;

    @PostMapping(value = "/registration")
    public User createStock(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);
        if (user != null) {
            sendMail.sendNewPasswordMailToUser(user, createUserRequest.getPassword());
        }
        return user;
    }
}