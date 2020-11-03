package by.itechart.security.service;

import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;
import by.itechart.model.domain.Security;
import by.itechart.exception.CustomException;
import org.springframework.stereotype.Service;
import by.itechart.security.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static by.itechart.utils.ProjectProperties.USER_DETAIL_NOT_FOUND;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailServiceImpl implements UserDetailsService {

    private final SecurityRepository securityRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Security> security = Optional.ofNullable(securityRepository.findByLogin(login));
        if (security.isEmpty()) {
            log.info("Method:loadUserByUsername. Security is empty!");
            throw new CustomException(USER_DETAIL_NOT_FOUND);
        }
        return new org.springframework.security.core.userdetails.User(
                security.get().getLogin(),
                security.get().getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(security.get().getUserRole())
        );
    }
}