package by.itechart.model.response;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CreateUserRequest {

   private String email;

   private String login;

   private String password;
}