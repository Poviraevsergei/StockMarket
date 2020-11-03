package by.itechart.exception;

import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private HttpStatus statusCode;
    private Timestamp timestamp;
    private String message;
    private Throwable throwable;
}