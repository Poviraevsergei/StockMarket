package by.itechart.controller;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import by.itechart.service.OpenApiService;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import by.itechart.model.response.ComparisonResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(MockitoJUnitRunner.class)
class OpenApiControllerTest {

    protected MockMvc mvc;

    @Mock
    private OpenApiService openApiService;

    @InjectMocks
    private OpenApiController openApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(openApiController).build();
    }

    @Test
    void comparison() throws Exception {
        when(openApiService.comparison(anyString(), anyString())).thenReturn(Optional.of(new ComparisonResponse()));

        MvcResult result = mvc.perform(get("/comparison")
                .param("firstTicker", anyString())
                .param("secondTicker", anyString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(openApiService, times(1)).comparison(anyString(), anyString());
    }

    @Test
    void comparisonForTheYear() throws Exception {
        when(openApiService.comparisonForTheYear(anyString())).thenReturn(Optional.of(new ComparisonResponse()));

        MvcResult result = mvc.perform(get("/comparisonForTheYear")
                .param("ticker", anyString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(openApiService, times(1)).comparisonForTheYear(anyString());
    }
}