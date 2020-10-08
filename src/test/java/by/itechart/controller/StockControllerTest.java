package by.itechart.controller;

import org.junit.jupiter.api.Test;
import by.itechart.model.domain.Stock;
import by.itechart.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import by.itechart.serviceFeign.StockServiceFeign;
import com.fasterxml.jackson.databind.ObjectMapper;
import by.itechart.model.response.StockCandlesResponse;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import by.itechart.model.response.CompanyStockForTheDayResponse;
import by.itechart.model.response.CompanyStockForTheYearResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Date;
import java.util.Optional;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = StockController.class)
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockServiceFeign stockServiceFeign;

    @MockBean
    private StockService stockService;

    private Stock stock;
    private CompanyStockForTheDayResponse companyStockForTheDayResponse;
    private CompanyStockForTheYearResponse companyStockForTheYearResponse;
    private StockCandlesResponse stockCandlesResponse;

    @BeforeEach
    void setUp() {
        stock = new Stock();
        companyStockForTheDayResponse = new CompanyStockForTheDayResponse();
        companyStockForTheYearResponse = new CompanyStockForTheYearResponse();
        stockCandlesResponse = new StockCandlesResponse();
        stock.setOpenPrice("113.92");
        stock.setClosePrice("113.02");
        stock.setTicker("MCDS");
        stock.setOpenPrice("2020-10-05");
        stock.setDate(new Date());
        stock.setAnnualDividends("0.75");
        stock.setLowestAnnualPrice("53.1525");
        stock.setHighestAnnualPrice("153.1525");
        stock.setOpenPrice("137.98");
    }

    @Test
    void findAllStock() throws Exception {
        List<Stock> resultList = new ArrayList<>();
        resultList.add(stock);
        when(stockService.findAllStock()).thenReturn(resultList);

        mockMvc.perform(get("/stock/getAllStocks"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(stockService).findAllStock();
    }

    @Test
    void findById() throws Exception {
        when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));

        mockMvc.perform(get("/stock/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        verify(stockService).findById(anyLong());
    }

    @Test
    void getCompanyStockForTheDay() throws Exception {
        when(stockServiceFeign.getCompanyStockForTheDay(anyString())).thenReturn(companyStockForTheDayResponse);

        mockMvc.perform(get("/stock/getCompanyStockForTheDay?ticker=bla"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        verify(stockServiceFeign).getCompanyStockForTheDay(anyString());
    }

    @Test
    void getCompanyStockForTheYear() throws Exception {
        when(stockServiceFeign.getCompanyStockForTheYear(anyString())).thenReturn(companyStockForTheYearResponse);

        mockMvc.perform(get("/stock/getCompanyStockForTheYear?ticker=TSLA"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        verify(stockServiceFeign).getCompanyStockForTheYear(anyString());
    }

    @Test
    void getStockCandles() throws Exception {
        when(stockServiceFeign.getStockCandles(anyString(), anyString(), anyString(), anyString())).thenReturn(stockCandlesResponse);

        mockMvc.perform(get("/stock/getStockCandles?ticker=TSLA&from=2020-02-02&to=2020-09-09&resolution=1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        verify(stockServiceFeign).getStockCandles(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void createStock() throws Exception {
        when(stockService.createStock(any())).thenReturn(stock);

        mockMvc.perform(post("/stock/create")
                .content(new ObjectMapper().writeValueAsString(stock))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void updateStock() throws Exception {
        when(stockService.updateStock(stock)).thenReturn(stock);

        mockMvc.perform(put("/stock/update")
                .content(new ObjectMapper().writeValueAsString(stock))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
        verify(stockService).updateStock(any());
    }

    @Test
    void deleteStock() throws Exception {
        mockMvc.perform(delete("/stock/{id}", 1))
                .andExpect(status().isOk());
        verify(stockService).deleteStockById(anyLong());
    }
}