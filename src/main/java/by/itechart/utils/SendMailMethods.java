package by.itechart.utils;

import by.itechart.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static by.itechart.utils.ProjectProperties.MAIL_SUBJECT;
import static by.itechart.utils.ProjectProperties.STOCK_MARKET_MAIL_ADDRESS;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SendMailMethods {

    private final JavaMailSender javaMailSender;

    public void sendReminderMailToUser(User user, Long daysDifference) {
        final String MAIL_TEXT = "Dear " + user.getSecurity().getLogin() + ", your subscription from stock-market.com will be blocked" +
                (daysDifference == 0L ? " today." : " after " + daysDifference +
                        (daysDifference == 1L ? " day." : " days.")) + " We recommend paying attention to this."
                + System.lineSeparator() + "Best regards, support team stock-market.com.";
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(STOCK_MARKET_MAIL_ADDRESS);
        mail.setSubject(MAIL_SUBJECT);
        mail.setText(MAIL_TEXT);
        javaMailSender.send(mail);
    }

    public void sendNewPasswordMailToUser(User user, String password) {
        final String MAIL_TEXT = "Dear " + user.getSecurity().getLogin() + "! We are glad to welcome you to our website."
                + System.lineSeparator() + "Your login: " + user.getSecurity().getLogin()
                + System.lineSeparator() + "Your password: " + password
                + System.lineSeparator() + "We recommend change password on the site."
                + System.lineSeparator() + "Best regards, support team stock-market.com.";
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(STOCK_MARKET_MAIL_ADDRESS);
        mail.setSubject(MAIL_SUBJECT);
        mail.setText(MAIL_TEXT);
        javaMailSender.send(mail);
    }
}