
package by.itechart.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "c",
        "h",
        "l",
        "o",
        "pc",
        "t"
})
public class CompanyStockForTheDayResponse {

    @JsonProperty("c")
    private Double currentPrice;
    @JsonProperty("h")
    private Double highPrice;
    @JsonProperty("l")
    private Double lowPrice;
    @JsonProperty("o")
    private Double openPrice;
    @JsonProperty("pc")
    private Double previousPrice;
    @JsonProperty("t")
    private Integer time;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}