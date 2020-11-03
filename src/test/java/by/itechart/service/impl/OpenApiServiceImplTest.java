package by.itechart.service.impl;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import by.itechart.model.domain.Stock;
import org.mockito.MockitoAnnotations;
import by.itechart.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
class OpenApiServiceImplTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private OpenApiServiceImpl openApiService;

    private final Stock stock = new Stock();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        stock.setOpenPrice("113.92");
        stock.setClosePrice("113.02");
        stock.setTicker("MCDS");
        stock.setDate(LocalDate.now());
        stock.setAnnualDividends("0.75");
        stock.setLowestAnnualPrice("53.1525");
        stock.setHighestAnnualPrice("153.1525");
    }

    @Test
    void comparison() {
        when(stockService.findByDateAndTicker(any(), anyString())).thenReturn(Optional.of(stock));

        assertNotNull(openApiService.comparison(anyString(), anyString()));
        verify(stockService, times(2)).findByDateAndTicker(any(), anyString());
    }

    @Test
    void comparisonForTheYear() {
        when(stockService.findByDateAndTicker(any(), anyString())).thenReturn(Optional.of(stock));

        assertNotNull(openApiService.comparisonForTheYear("TSLA"));
        verify(stockService, times(2)).findByDateAndTicker(any(), anyString());
    }
}