package by.itechart.model.response;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyStockForTheDayResponse {

    @JsonProperty("c")
    private String currentPrice;

    @JsonProperty("h")
    private String highPrice;

    @JsonProperty("l")
    private String lowPrice;

    @JsonProperty("o")
    private String openPrice;

    @JsonProperty("pc")
    private String previousPrice;

    @JsonProperty("t")
    private String time;
}