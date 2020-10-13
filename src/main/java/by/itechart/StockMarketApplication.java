package by.itechart;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"by.itechart"})
public class StockMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockMarketApplication.class, args);
    }
}