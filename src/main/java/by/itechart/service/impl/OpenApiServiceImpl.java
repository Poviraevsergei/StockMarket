package by.itechart.service.impl;

import by.itechart.model.domain.Stock;
import lombok.RequiredArgsConstructor;
import by.itechart.service.StockService;
import by.itechart.service.OpenApiService;
import by.itechart.exception.CustomException;
import org.springframework.stereotype.Service;
import by.itechart.model.response.ComparisonResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.time.LocalDate;

import static by.itechart.utils.ProjectProperties.COMPARISON_EXCEPTION;
import static by.itechart.utils.ProjectProperties.COMPARISON_FOR_THE_YEAR_EXCEPTION;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OpenApiServiceImpl implements OpenApiService {

    private final StockService stockService;

    @Override
    public Optional<ComparisonResponse> comparison(String firstTicker, String secondTicker) {
        Stock firstStock = stockService.findByDateAndTicker(LocalDate.now(), firstTicker).orElseThrow(() -> new CustomException(COMPARISON_EXCEPTION));
        Stock secondStock = stockService.findByDateAndTicker(LocalDate.now(), secondTicker).orElseThrow(() -> new CustomException(COMPARISON_EXCEPTION));
        return Optional.of(compareStocks(firstStock, secondStock));
    }

    @Override
    public Optional<ComparisonResponse> comparisonForTheYear(String ticker) {
        Stock todayStock = stockService.findByDateAndTicker(LocalDate.now(), ticker).orElseThrow(() -> new CustomException(COMPARISON_FOR_THE_YEAR_EXCEPTION));
        Stock lastYearStock = stockService.findByDateAndTicker(LocalDate.now().minusYears(1), ticker).orElseThrow(() -> new CustomException(COMPARISON_FOR_THE_YEAR_EXCEPTION));
        return Optional.of(compareStocks(todayStock, lastYearStock));
    }

    private ComparisonResponse compareStocks(Stock firstStock, Stock secondStock) {
        ComparisonResponse comparisonResponse = new ComparisonResponse();
        comparisonResponse.setFirstOpenPrice(firstStock.getOpenPrice());
        comparisonResponse.setFirstClosePrice(firstStock.getClosePrice());
        comparisonResponse.setFirstTicker(firstStock.getTicker());
        comparisonResponse.setFirstDate(firstStock.getDate());
        comparisonResponse.setFirstAnnualDividends(firstStock.getAnnualDividends());
        comparisonResponse.setFirstLowestAnnualPricePerYear(firstStock.getLowestAnnualPrice());
        comparisonResponse.setFirstHighestAnnualPricePerYear(firstStock.getHighestAnnualPrice());
        comparisonResponse.setSecondOpenPrice(secondStock.getOpenPrice());
        comparisonResponse.setSecondClosePrice(secondStock.getClosePrice());
        comparisonResponse.setSecondTicker(secondStock.getTicker());
        comparisonResponse.setSecondDate(secondStock.getDate());
        comparisonResponse.setSecondAnnualDividends(secondStock.getAnnualDividends());
        comparisonResponse.setSecondLowestAnnualPricePerYear(secondStock.getLowestAnnualPrice());
        comparisonResponse.setSecondHighestAnnualPricePerYear(secondStock.getHighestAnnualPrice());
        return comparisonResponse;
    }
}