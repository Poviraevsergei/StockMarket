package by.itechart.controller;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import by.itechart.model.domain.User;
import org.mockito.MockitoAnnotations;
import by.itechart.model.domain.Status;
import by.itechart.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import by.itechart.model.domain.Security;
import org.mockito.junit.MockitoJUnitRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.test.web.servlet.MockMvc;
import by.itechart.model.response.CreateUserRequest;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.hamcrest.MatcherAssert.assertThat;
import static by.itechart.utils.ProjectProperties.TEST_TICKER;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {

    MockMvc mvc;

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    private final User user = new User();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(userController).build();
        user.setEmail("test-email@gmail.com");
        user.setCreated(LocalDate.now());
        user.setChanged(LocalDate.now());
        user.setStatus(new Status());
        user.setSecurity(new Security());
    }

    @Test
    void findAllUsers() throws Exception {
        List<User> list = new ArrayList<>();
        list.add(new User());
        when(userService.findAllUsers()).thenReturn(Optional.of(list));

        MvcResult result = mvc.perform(get("/user/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).findAllUsers();
    }

    @Test
    void findUserById() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user));

        MvcResult result = mvc.perform(get("/user/{id}", anyLong()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).findUserById(anyLong());
    }

    @Test
    void addCompanyToAccount() throws Exception {
        when(userService.addCompanyToAccount(anyString())).thenReturn(Optional.of(user));

        MvcResult result = mvc.perform(get("/user/addCompany")
                .param("ticker", TEST_TICKER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).addCompanyToAccount(anyString());
    }

    @Test
    void createUser() throws Exception {
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(user);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(new CreateUserRequest());

        MvcResult result = mvc.perform(post("/user/create").contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).createUser(any());
    }

    @Test
    void updateUser() throws Exception {
        when(userService.updateUser(any(User.class))).thenReturn(user);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(new User());

        MvcResult result = mvc.perform(put("/user/update").contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).updateUser(any(User.class));
    }

    @Test
    void deleteUserById() throws Exception {
        MvcResult result = mvc.perform(delete("/user/{id}", anyLong()))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(userService, times(1)).deleteUserById(anyLong());
    }
}