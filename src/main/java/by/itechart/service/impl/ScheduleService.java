package by.itechart.service.impl;

import by.itechart.model.response.CompanyStockForTheDayResponse;
import by.itechart.model.response.CompanyStockForTheYearResponse;
import by.itechart.utils.SendMailMethods;
import lombok.extern.slf4j.Slf4j;
import by.itechart.model.domain.User;
import by.itechart.model.domain.Stock;
import lombok.RequiredArgsConstructor;
import by.itechart.service.UserService;
import by.itechart.model.domain.Company;
import by.itechart.service.StockService;
import by.itechart.service.CompanyService;
import org.springframework.stereotype.Service;
import by.itechart.service.serviceFeign.StockServiceFeign;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import static by.itechart.utils.ProjectProperties.HIGHEST_ANNUAL_PRICE;
import static by.itechart.utils.ProjectProperties.LOWEST_ANNUAL_PRICE;
import static by.itechart.utils.ProjectProperties.DIVIDEND_PER_SHARE_ANNUAL;
import static by.itechart.utils.ProjectProperties.CRON_ONE_TIME_TO_DAY;
import static by.itechart.utils.ProjectProperties.ROLE_USER;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleService {

    private final CompanyService companyService;
    private final UserService userService;
    private final StockService stockService;
    private final StockServiceFeign stockServiceFeign;
    private final SendMailMethods sendMail;

    @Scheduled(cron = (CRON_ONE_TIME_TO_DAY))
    private void updateStockData() {
        Optional<List<Company>> allCompanies = companyService.findAllCompanyFromDb();
        if (allCompanies.isEmpty()) {
            log.warn("companyService.findAllCompanyFromDb() return empty list!");
            return;
        }
        for (Company company : allCompanies.get()) {
            Optional<CompanyStockForTheYearResponse> companyStockForTheYear = stockServiceFeign.getCompanyStockForTheYear(company.getTicker());
            if (companyStockForTheYear.isEmpty()) {
                log.warn("Something wrong, companyStockForTheYear is empty for " + company.getTicker());
                continue;
            }
            Optional<CompanyStockForTheDayResponse> companyStockForTheDay = stockServiceFeign.getCompanyStockForTheDay(company.getTicker());
            if (companyStockForTheDay.isEmpty()) {
                log.warn("Something wrong, companyStockForTheDay is empty for " + company.getTicker());
                continue;
            }
            if (!updateStock(company, companyStockForTheYear.get(), companyStockForTheDay.get())) {
                log.warn("Something wrong, updateStock method return false. Company ticker = " + company.getTicker());
            }
        }
        log.info("At " + Timestamp.valueOf(LocalDateTime.now()) + " stock data was update.");
    }

    private boolean updateStock(Company company, CompanyStockForTheYearResponse companyStockForTheYear, CompanyStockForTheDayResponse companyStockForTheDay) {
        String annualDividends = getDividends(companyStockForTheYear);
        Stock stock = new Stock();
        stock.setTicker(company.getTicker());
        stock.setHighestAnnualPrice(String.valueOf(companyStockForTheYear.getAdditionalProperties().get(HIGHEST_ANNUAL_PRICE)));
        stock.setLowestAnnualPrice(String.valueOf(companyStockForTheYear.getAdditionalProperties().get(LOWEST_ANNUAL_PRICE)));
        stock.setAnnualDividends(annualDividends);
        stock.setDate(LocalDate.now());
        stock.setOpenPrice(companyStockForTheDay.getOpenPrice());
        stock.setClosePrice(companyStockForTheDay.getPreviousPrice());
        stock.setCompany(company);
        Stock result = stockService.createStock(stock);
        return result != null;
    }

    public String getDividends(CompanyStockForTheYearResponse companyStockForTheYear) {
        String dividends = String.valueOf(companyStockForTheYear.getAdditionalProperties().get(DIVIDEND_PER_SHARE_ANNUAL));
        return !dividends.equals("null") ? dividends : "-";
    }

    @Scheduled(cron = (CRON_ONE_TIME_TO_DAY))
    private void checkUserStatus() {
        Optional<List<User>> allUsers = userService.findAllUsers();
        if (allUsers.isEmpty()) {
            log.warn("userService.findAllUsers() return empty list!");
            return;
        }
        for (User user : allUsers.get()) {
            LocalDate expiryUserTime = user.getStatus().getExpiryDate();
            LocalDate timeToday = LocalDate.now();
            LocalDate timeTodayPlusThreeDays = LocalDate.now().plusDays(3);
            if (expiryUserTime.isAfter(timeToday.minusDays(1))) {
                if (timeTodayPlusThreeDays.isAfter(expiryUserTime.minusDays(1))) {
                    Period period = Period.between(timeToday, expiryUserTime);
                    long daysDifference = Long.valueOf(period.getDays());
                    sendMail.sendReminderMailToUser(user,daysDifference);
                    log.info("User with id=" + user.getId() + " will receive status = false" +
                            (daysDifference == 0L ? " today." : " after " + daysDifference + (daysDifference == 1L ? " day." : " days.")));
                }
            } else {
                if (user.getStatus().getIsActive()) {
                    user.getStatus().setIsActive(false);
                    user.getSecurity().setUserRole(ROLE_USER);
                    log.info("User with id=" + user.getId() + " got status = false. Role = USER.");
                    userService.updateUser(user);
                }
            }
        }
    }
}