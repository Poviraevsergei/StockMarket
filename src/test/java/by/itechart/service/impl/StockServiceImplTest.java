package by.itechart.service.impl;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import by.itechart.model.domain.Stock;
import by.itechart.model.domain.Company;
import org.junit.jupiter.api.BeforeEach;
import by.itechart.repository.StockRepository;
import by.itechart.repository.CompanyRepository;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.anyLong;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class StockServiceImplTest {

    @Mock
    StockRepository stockRepository;

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock();
        stock.setOpenPrice("113.92");
        stock.setClosePrice("113.02");
        stock.setTicker("MCDS");
        stock.setOpenPrice("2020-10-05");
        stock.setDate(Timestamp.valueOf(LocalDateTime.now()));
        stock.setAnnualDividends("0.75");
        stock.setLowestAnnualPrice("53.1525");
        stock.setHighestAnnualPrice("153.1525");
        stock.setOpenPrice("137.98");
    }

    @Test
    void createStock() {
        when(stockRepository.save(stock)).thenReturn(stock);

        assertThat(stockService.createStock(stock), equalTo(stock));
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    void findAllStock() {
        List<Stock> stockList = new ArrayList<>();
        stockList.add(stock);
        when(stockRepository.findAll()).thenReturn(stockList);

        assertThat(stockService.findAllStock(), allOf(notNullValue(), equalTo(stockList)));
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        final Long id = 1L;
        when(stockRepository.findById(id)).thenReturn(Optional.of(stock));
        when(companyRepository.findCompaniesByTicker(anyString())).thenReturn(new Company());

        assertThat(stockService.findById(id), equalTo(Optional.of(stock)));
        verify(stockRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateStock() {
        when(stockRepository.save(stock)).thenReturn(stock);
        when(companyRepository.findCompaniesByTicker(anyString())).thenReturn(new Company());

        assertThat(stockService.updateStock(stock), equalTo(stock));
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    void deleteStockById() {
        stockService.deleteStockById(anyLong());

        verify(stockRepository, times(1)).deleteById(anyLong());
    }
}