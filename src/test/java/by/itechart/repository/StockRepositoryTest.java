package by.itechart.repository;

import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import by.itechart.model.domain.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static by.itechart.utils.ProjectProperties.TEST_TICKER;

@DataJpaTest
@RunWith(SpringRunner.class)
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    private final Stock stock = new Stock();

    @BeforeEach
    void setUp() {
        stock.setOpenPrice("113.92");
        stock.setClosePrice("113.02");
        stock.setTicker("MCDS");
        stock.setDate(LocalDate.now());
        stock.setAnnualDividends("0.75");
        stock.setLowestAnnualPrice("53.1525");
        stock.setHighestAnnualPrice("153.1525");
    }

    @Test
    void findByDateAndTicker() {
        Stock savedStock = stockRepository.save(stock);
        Stock findStock = stockRepository.findByDateAndTicker(LocalDate.now(), stock.getTicker()).orElseThrow();

        assertThat(findStock, is(notNullValue()));
        assertThat(savedStock.getTicker(), is(findStock.getTicker()));
        assertThat(savedStock.getDate(), is(findStock.getDate()));
    }

    @Test
    public void findAll() {
        final int STOCKS_COUNT_IN_DATABASE = 8;
        List<Stock> resultList = (List<Stock>) stockRepository.findAll();

        assertThat(resultList, is(notNullValue()));
        assertThat(resultList.size(), is(STOCKS_COUNT_IN_DATABASE));
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
        savedStock.setTicker(TEST_TICKER);
        Stock resultStock = stockRepository.save(savedStock);

        assertThat(resultStock.getTicker(), is(TEST_TICKER));
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
