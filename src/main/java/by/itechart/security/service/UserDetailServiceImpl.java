package by.itechart.security.service;

import by.itechart.model.domain.Security;
import by.itechart.security.repository.SecurityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        }
        return new org.springframework.security.core.userdetails.User(
                security.get().getLogin(),
                security.get().getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(security.get().getUserRole())
        );
    }
}