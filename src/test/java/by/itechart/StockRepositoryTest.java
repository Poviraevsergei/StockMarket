package by.itechart;

import org.junit.jupiter.api.Test;
import by.itechart.model.domain.Stock;
import org.junit.jupiter.api.BeforeEach;
import by.itechart.repository.StockRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
public class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock();
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
    public void findAll() {
        int userCountInDatabase = 3;
        List<Stock> resultList = (List<Stock>) stockRepository.findAll();

        assertThat(resultList, is(notNullValue()));
        assertThat(resultList.size(), is(userCountInDatabase));
    }

    @Test
    public void findById() {
        Stock savedStock = stockRepository.save(stock);
        Stock findStock = stockRepository.findById(savedStock.getId()).orElseThrow();

        assertThat(findStock.getId(), is(equalTo(savedStock.getId())));
    }

    @Test
    public void update() {
        Stock savedStock = stockRepository.save(stock);
        savedStock.setTicker("TEST");
        Stock resultStock = stockRepository.save(savedStock);

        assertThat(resultStock.getTicker(), is("TEST"));
    }

    @Test
    public void save() {
        List<Stock> listStocks = (List<Stock>) stockRepository.findAll();
        stockRepository.save(stock);
        List<Stock> newListStocks = (List<Stock>) stockRepository.findAll();

        assertThat(newListStocks.size(), allOf(notNullValue(), equalTo(listStocks.size() + 1)));
    }

    @Test
    public void deleteById() {
        Stock resultStock = stockRepository.save(stock);
        stockRepository.deleteById(resultStock.getId());

        assertThat(stockRepository.findById(resultStock.getId()).orElse(null), is(nullValue()));
    }
}