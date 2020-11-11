package by.itechart.service.impl;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import by.itechart.model.domain.Stock;
import org.mockito.MockitoAnnotations;
import by.itechart.model.domain.Company;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.MockitoJUnitRunner;
import by.itechart.repository.StockRepository;
import by.itechart.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;

import static by.itechart.utils.ProjectProperties.TEST_TICKER;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.anyLong;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.anyString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

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
        Stock newStock = stockService.findByDateAndTicker("2019-11-04", TEST_TICKER).orElseThrow();

        assertThat(newStock, equalTo(stock));
        verify(stockRepository, times(1)).findByDateAndTicker(any(LocalDate.class), anyString());
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
