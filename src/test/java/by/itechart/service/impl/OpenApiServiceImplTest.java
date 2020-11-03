package by.itechart.service.impl;

import by.itechart.model.domain.Stock;
import by.itechart.model.response.ComparisonResponse;
import by.itechart.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
class OpenApiServiceImplTest {

    @Mock
    private ComparisonResponse comparisonResponse;

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
