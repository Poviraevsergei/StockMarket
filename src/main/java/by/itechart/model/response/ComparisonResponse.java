package by.itechart.model.response;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
public class ComparisonResponse {

    private String firstOpenPrice;

    private String firstClosePrice;

    private String firstTicker;

    private LocalDate firstDate;

    private String firstAnnualDividends;

    private String firstLowestAnnualPricePerYear;

    private String firstHighestAnnualPricePerYear;

    private String secondOpenPrice;

    private String secondClosePrice;

    private String secondTicker;

    private LocalDate secondDate;

    private String secondAnnualDividends;

    private String secondLowestAnnualPricePerYear;

    private String secondHighestAnnualPricePerYear;
}