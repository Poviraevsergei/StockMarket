package by.itechart.repository;

import by.itechart.model.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@RunWith(SpringRunner.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final User user = new User();

    @BeforeEach
    void setUp() {
        user.setEmail("test-email@gmail.com");
        user.setCreated(LocalDate.now());
        user.setChanged(LocalDate.now());
    }

    @Test
    void findUserByEmail() {
        User savedUser = userRepository.save(user);
        User findUser = userRepository.findUserByEmail(savedUser.getEmail()).orElseThrow();

        assertThat(findUser, is(notNullValue()));
        assertThat(savedUser.getEmail(), is(findUser.getEmail()));
    }

    @Test
    public void deleteById() {
        User resultUser = userRepository.save(user);
        userRepository.deleteById(resultUser.getId());

        assertThat(userRepository.findById(resultUser.getId()).orElse(null), is(nullValue()));
    }

    @Test
    public void save() {
        List<User> listUsers = (List<User>) userRepository.findAll();
        userRepository.save(user);
        List<User> newListUsers = (List<User>) userRepository.findAll();

        assertThat(newListUsers.size(), allOf(notNullValue(), equalTo(listUsers.size() + 1)));
    }

    @Test
    public void findById() {
        User savedUser = userRepository.save(user);
        User findUser = userRepository.findById(savedUser.getId()).orElseThrow();

        assertThat(savedUser.getId(), is(equalTo(findUser.getId())));
    }

    @Test
    public void findAll() {
        final int USERS_COUNT_IN_DATABASE = 3;
        List<User> resultList = (List<User>) userRepository.findAll();

        assertThat(resultList, is(notNullValue()));
        assertThat(resultList.size(), is(USERS_COUNT_IN_DATABASE));
    }
}