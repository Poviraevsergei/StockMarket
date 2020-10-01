
package by.itechart.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "country",
        "currency",
        "exchange",
        "finnhubIndustry",
        "ipo",
        "logo",
        "marketCapitalization",
        "name",
        "phone",
        "shareOutstanding",
        "ticker",
        "weburl"
})
@Data
public class CompanyResponse {

    @JsonProperty("country")
    private String country;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("finnhubIndustry")
    private String finnhubIndustry;
    @JsonProperty("ipo")
    private String ipo;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("marketCapitalization")
    private Integer marketCapitalization;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("shareOutstanding")
    private Double shareOutstanding;
    @JsonProperty("ticker")
    private String ticker;
    @JsonProperty("weburl")
    private String weburl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}