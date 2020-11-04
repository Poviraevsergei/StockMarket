package by.itechart.controller;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import by.itechart.model.domain.Stock;
import org.mockito.MockitoAnnotations;
import by.itechart.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.mockito.junit.MockitoJUnitRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import by.itechart.model.response.StockCandlesResponse;
import by.itechart.service.serviceFeign.StockServiceFeign;
import com.fasterxml.jackson.databind.SerializationFeature;
import by.itechart.model.response.CompanyStockForTheDayResponse;
import by.itechart.model.response.CompanyStockForTheYearResponse;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyLong;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.anyString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static by.itechart.utils.ProjectProperties.TEST_TICKER;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@RunWith(MockitoJUnitRunner.class)
class StockControllerTest {

    @Mock
    private StockService stockService;

    @Mock
    private StockServiceFeign stockServiceFeign;

    @InjectMocks
    private StockController stockController;

    protected MockMvc mvc;

    private final Stock stock = new Stock();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(stockController).build();
        stock.setOpenPrice("113.92");
        stock.setClosePrice("113.02");
        stock.setTicker("MCDS");
        stock.setDate(LocalDate.now());
        stock.setAnnualDividends("0.75");
        stock.setLowestAnnualPrice("53.1525");
        stock.setHighestAnnualPrice("153.1525");
    }

    @Test
    void findAllStock() throws Exception {
        List<Stock> list = new ArrayList<>();
        list.add(stock);
        when(stockService.findAllStock()).thenReturn(Optional.of(new ArrayList()));

        MvcResult result = mvc.perform(get("/stock/getAllStocks"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(stockService, times(1)).findAllStock();
    }

    @Test
    void findByDateAndTicker() throws Exception {
        when(stockService.findByDateAndTicker(anyString(), anyString())).thenReturn(Optional.of(stock));

        MvcResult result = mvc.perform(get("/stock/getStock")
                .param("date", anyString())
                .param("ticker", anyString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(stockService, times(1)).findByDateAndTicker(anyString(),anyString());
    }

    @Test
    void getCompanyStockForTheDay() throws Exception {
        when(stockServiceFeign.getCompanyStockForTheDay(anyString())).thenReturn(Optional.of(new CompanyStockForTheDayResponse()));

        MvcResult result = mvc.perform(get("/stock/getCompanyStockForTheDay")
                .param("ticker", TEST_TICKER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(stockServiceFeign, times(1)).getCompanyStockForTheDay(anyString());
    }

    @Test
    void getCompanyStockForTheYear() throws Exception {
        when(stockServiceFeign.getCompanyStockForTheYear(anyString())).thenReturn(Optional.of(new CompanyStockForTheYearResponse()));

        MvcResult result = mvc.perform(get("/stock/getCompanyStockForTheYear")
                .param("ticker", TEST_TICKER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(stockServiceFeign, times(1)).getCompanyStockForTheYear(anyString());
    }

    @Test
    void getStockCandles() throws Exception {
        when(stockServiceFeign.getStockCandles(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.of(new StockCandlesResponse()));

        MvcResult result = mvc.perform(get("/stock/getStockCandles")
                .param("ticker", anyString())
                .param("from", anyString())
                .param("to", anyString())
                .param("resolution", anyString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(stockServiceFeign, times(1)).getStockCandles(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void createStock() throws Exception {
        when(stockService.createStock(any(Stock.class))).thenReturn(stock);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(new Stock());

        MvcResult result = mvc.perform(post("/stock/create").contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(stockService, times(1)).createStock(any(Stock.class));
    }

    @Test
    void updateStock() throws Exception {
        when(stockService.updateStock(any(Stock.class))).thenReturn(stock);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(new Stock());

        MvcResult result = mvc.perform(put("/stock/update").contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(stockService, times(1)).updateStock(any(Stock.class));
    }

    @Test
    void deleteStock() throws Exception {
        MvcResult result = mvc.perform(delete("/stock/{id}", anyLong()))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(stockService, times(1)).deleteStockById(anyLong());
    }
}
