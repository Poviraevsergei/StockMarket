package by.itechart.utils;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import by.itechart.model.domain.User;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import by.itechart.model.domain.Security;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
class SendMailMethodsTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private SendMailMethods sendMailMethods;

    private final User user = new User();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Security security = new Security();
        security.setLogin("test-login");
        user.setSecurity(security);
    }

    @Test
    void sendReminderMailToUser() {
        sendMailMethods.sendReminderMailToUser(user, anyLong());

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));

    }

    @Test
    void sendNewPasswordMailToUser() {
        sendMailMethods.sendNewPasswordMailToUser(user, anyString());

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}