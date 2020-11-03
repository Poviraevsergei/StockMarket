package by.itechart.service.impl;

import by.itechart.model.domain.Company;
import by.itechart.model.domain.Stock;
import by.itechart.repository.CompanyRepository;
import by.itechart.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;

@RunWith(MockitoJUnitRunner.class)
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    private final Stock stock = new Stock();
    private final Company company = new Company();

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
    void findAllStock() {
        List<Stock> stockList = new ArrayList<>();
        stockList.add(stock);
        when(stockRepository.findAll()).thenReturn(stockList);
        List<Stock> result = stockService.findAllStock().orElseThrow();

        assertThat(result, allOf(notNullValue(), equalTo(stockList)));
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(stock));
        Stock newStock = stockService.findById(anyLong()).orElseThrow();

        assertThat(newStock, equalTo(stock));
        verify(stockRepository, times(1)).findById(anyLong());
    }

    @Test
    void findByDateAndTicker() {
        when(stockRepository.findByDateAndTicker(any(), anyString())).thenReturn(Optional.of(stock));
        Stock newStock = stockService.findByDateAndTicker(any(), anyString()).orElseThrow();

        assertThat(newStock, equalTo(stock));
        verify(stockRepository, times(1)).findByDateAndTicker(any(), anyString());
    }

    @Test
    void createStock() {
        when(stockRepository.save(any())).thenReturn(stock);
        when(companyRepository.findCompaniesByTicker(anyString())).thenReturn(Optional.of(company));
        Stock newStock = stockService.createStock(stock);

        assertThat(newStock, equalTo(stock));
        verify(stockRepository, times(1)).save(any());
    }

    @Test
    void updateStock() {
        when(stockRepository.save(any())).thenReturn(stock);
        when(companyRepository.findCompaniesByTicker(anyString())).thenReturn(Optional.of(company));
        Stock newStock = stockService.updateStock(stock);

        assertThat(newStock, equalTo(stock));
        verify(stockRepository, times(1)).save(any());
    }

    @Test
    void deleteStockById() {
        stockService.deleteStockById(anyLong());

        verify(stockRepository, times(1)).deleteById(anyLong());
    }
}
