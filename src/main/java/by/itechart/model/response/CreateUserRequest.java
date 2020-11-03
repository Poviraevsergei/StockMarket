package by.itechart.model.response;

import lombok.Data;

@Data
public class CreateUserRequest {

   private String email;

   private String login;

   private String password;
}