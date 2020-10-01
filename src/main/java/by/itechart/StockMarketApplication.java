package by.itechart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StockMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockMarketApplication.class, args);
    }
}