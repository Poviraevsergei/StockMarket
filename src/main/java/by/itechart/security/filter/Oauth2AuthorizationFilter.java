package by.itechart.security.filter;

import by.itechart.model.domain.User;
import lombok.RequiredArgsConstructor;
import by.itechart.utils.SendMailMethods;
import org.springframework.stereotype.Component;
import by.itechart.service.impl.UserServiceImpl;
import by.itechart.model.response.CreateUserRequest;
import org.springframework.security.core.Authentication;
import by.itechart.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;

import static by.itechart.utils.ProjectProperties.EMAIL;
import static by.itechart.config.BeanConfiguration.passwordEncoder;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Oauth2AuthorizationFilter implements Filter {

    private final UserServiceImpl userService;
    private final UserDetailServiceImpl userDetailsService;
    private final SendMailMethods sendMail;
    private final String randomPassword = Long.toHexString(Double.doubleToLongBits(Math.random()));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (!(authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User)) {
                OAuth2User user = new OAuth2AuthenticationToken((OAuth2User) authentication.getPrincipal(), authentication.getAuthorities(), authentication.getName()).getPrincipal();
                User authorizationUser = getUserFromEmail(user);
                updateSecurityContext(authentication, authorizationUser);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private User getUserFromEmail(OAuth2User oAuth2User) {
        String userEmail = oAuth2User.getAttribute(EMAIL);
        return userService.findUserByEmail(userEmail).orElseGet(() -> {
            CreateUserRequest createUserRequest = new CreateUserRequest();
            createUserRequest.setEmail(userEmail);
            createUserRequest.setLogin(userEmail);
            createUserRequest.setPassword(passwordEncoder().encode(randomPassword));
            User user = userService.createUser(createUserRequest);
            if (user != null) {
                sendMail.sendNewPasswordMailToUser(user, randomPassword);
            }
            return user;
        });
    }

    private void updateSecurityContext(Authentication authentication, User authorizationUser) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authorizationUser.getSecurity().getLogin());
        UsernamePasswordAuthenticationToken authentication1 = new UsernamePasswordAuthenticationToken(
                userDetails,
                authentication.getCredentials(),
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication1);
    }
}