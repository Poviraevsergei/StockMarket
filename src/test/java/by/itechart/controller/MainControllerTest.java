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
import by.itechart.utils.SendMailMethods;
import org.mockito.junit.MockitoJUnitRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.test.web.servlet.MockMvc;
import by.itechart.model.response.CreateUserRequest;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class MainControllerTest {

    protected MockMvc mvc;

    @Mock
    private UserService userService;

    @Mock
    private SendMailMethods sendMail;

    @InjectMocks
    private MainController mainController;

    private final User user = new User();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(mainController).build();
        user.setEmail("test-email@gmail.com");
        user.setCreated(LocalDate.now());
        user.setChanged(LocalDate.now());
        user.setStatus(new Status());
        user.setSecurity(new Security());
    }

    @Test
    void createUser() throws Exception {
        when(userService.createUser(any())).thenReturn(user);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setPassword("password");
        String requestJson = ow.writeValueAsString(createUserRequest);

        MvcResult result = mvc.perform(post("/registration").contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).createUser(any());
        verify(sendMail, times(1)).sendNewPasswordMailToUser(any(User.class), anyString());
    }

    @Test
    void changeToPremium() throws Exception {
        when(userService.changeToPremium(anyString())).thenReturn(Optional.of(user));

        MvcResult result = mvc.perform(put("/changeToPremium")
                .param("userLogin", anyString()))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(userService, times(1)).changeToPremium(anyString());
    }
}