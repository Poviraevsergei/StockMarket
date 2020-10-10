package by.itechart.service.serviceSchedule;

import lombok.extern.slf4j.Slf4j;
import by.itechart.model.domain.User;
import by.itechart.model.domain.Stock;
import lombok.RequiredArgsConstructor;
import by.itechart.service.UserService;
import by.itechart.model.domain.Company;
import by.itechart.service.StockService;
import by.itechart.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import by.itechart.service.serviceFeign.StockServiceFeign;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleService {

    private final CompanyService companyService;
    private final UserService userService;
    private final StockService stockService;
    private final StockServiceFeign stockServiceFeign;
    private final JavaMailSender javaMailSender;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateStockData() {
        List<Company> allCompanies = companyService.findAllCompanyFromDb();
        for (Company company : allCompanies) {
            String dividends = String.valueOf(stockServiceFeign.getCompanyStockForTheYear(company.getTicker()).getAdditionalProperties().get("dividendPerShareAnnual"));
            Stock stock = new Stock();
            stock.setTicker(company.getTicker());
            stock.setHighestAnnualPrice(String.valueOf(stockServiceFeign.getCompanyStockForTheYear(company.getTicker()).getAdditionalProperties().get("52WeekHigh")));
            stock.setLowestAnnualPrice(String.valueOf(stockServiceFeign.getCompanyStockForTheYear(company.getTicker()).getAdditionalProperties().get("52WeekLow")));
            stock.setAnnualDividends(!dividends.equals("null") ? dividends : "-");
            stock.setDate(new Date());
            stock.setOpenPrice(stockServiceFeign.getCompanyStockForTheDay(company.getTicker()).getOpenPrice());
            stock.setClosePrice(stockServiceFeign.getCompanyStockForTheDay(company.getTicker()).getPreviousPrice());
            stock.setCompany(company);
            stockService.createStock(stock);
        }
        log.info("At " + new Date() + " stock data was update.");
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkUserStatus() {
        List<User> allUsers = userService.findAllUsers();
        for (User user : allUsers) {
            Date expiryUserTime = dateReset(user.getRole().getExpiryDate());
            Date timeToday = dateReset(new Date());
            Date timeTodayPlusThreeDays = dateResetPlusThreeDays(timeToday);
            if (expiryUserTime.getTime() >= timeToday.getTime()) {
                if (timeTodayPlusThreeDays.after(expiryUserTime)) {
                    long daysDifference = TimeUnit.DAYS.convert(expiryUserTime.getTime() - timeToday.getTime(), TimeUnit.MILLISECONDS);

                    SimpleMailMessage mail = new SimpleMailMessage();
                    mail.setTo(user.getEmail());
                    mail.setFrom("stockitechart@gmail.com");
                    mail.setSubject("Announcement from stock-market.com");
                    mail.setText("Dear " + user.getLogin() + ", your subscription from stock-market.com will be blocked" +
                            (daysDifference == 0L ? " today." : " after " + daysDifference +
                                    (daysDifference == 1L ? " day." : " days.")) + " We recommend paying attention to this."
                            + System.lineSeparator() + "Best regards, support team stock-market.com.");
                    javaMailSender.send(mail);
                    log.info("User with id=" + user.getId() + " will receive status = false" + (daysDifference == 0L ? " today." : " after " + daysDifference) + (daysDifference == 1L ? " day." : " days."));
                }
            } else {
                if (user.getRole().getIsActive()) {
                    user.getRole().setIsActive(false);
                    log.info("User with id=" + user.getId() + " got status = false.");
                    userService.updateUser(user);
                }
            }
        }
    }

    public Date dateReset(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public Date dateResetPlusThreeDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}