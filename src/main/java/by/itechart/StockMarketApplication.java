package by.itechart;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"by.itechart"})
@EnableFeignClients
@EnableScheduling
public class StockMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockMarketApplication.class, args);
    }
}