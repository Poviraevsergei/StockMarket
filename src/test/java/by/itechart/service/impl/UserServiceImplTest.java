package by.itechart.service.impl;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import by.itechart.model.domain.User;
import org.mockito.MockitoAnnotations;
import by.itechart.model.domain.Status;
import org.junit.jupiter.api.BeforeEach;
import by.itechart.model.domain.Security;
import by.itechart.service.CompanyService;
import org.mockito.junit.MockitoJUnitRunner;
import by.itechart.repository.UserRepository;
import by.itechart.model.response.CreateUserRequest;
import by.itechart.security.repository.SecurityRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.anyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.hamcrest.MatcherAssert.assertThat;
import static by.itechart.utils.ProjectProperties.ROLE_CLIENT;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityRepository securityRepository;

    @Mock
    private CompanyService companyService;

    private final User user = new User();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user.setEmail("test-email@gmail.com");
        user.setCreated(LocalDate.now());
        user.setChanged(LocalDate.now());
        user.setStatus(new Status());
        user.setSecurity(new Security());
    }

    @Test
    void findAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userService.findAllUsers().orElseThrow();

        assertThat(result, allOf(notNullValue(), equalTo(userList)));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertThat(userService.findUserById(anyLong()), equalTo(Optional.of(user)));
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void findUserByEmail() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));

        assertThat(userService.findUserByEmail(anyString()), equalTo(Optional.of(user)));
        verify(userRepository, times(1)).findUserByEmail(anyString());
    }

    @Test
    void changeToPremium() {
        Security security = new Security();
        security.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        when(securityRepository.findByLogin(anyString())).thenReturn(security);

        User newUser = userService.changeToPremium(anyString()).orElseThrow();

        assertThat(newUser, equalTo(user));
        assertThat(newUser.getSecurity().getUserRole(), equalTo(ROLE_CLIENT));
        assertThat(newUser.getStatus().getIsActive(), equalTo(true));
        verify(userRepository, times(1)).findById(anyLong());
        verify(securityRepository, times(1)).findByLogin(anyString());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void createUser() {
        when(userRepository.save(any())).thenReturn(user);

        assertThat(userService.createUser(new CreateUserRequest()), equalTo(user));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void updateUser() {
        when(userRepository.save(any())).thenReturn(user);

        assertThat(userService.updateUser(any()), equalTo(user));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void deleteUserById() {
        userService.deleteUserById(anyLong());

        verify(userRepository, times(1)).deleteById(anyLong());
    }
}