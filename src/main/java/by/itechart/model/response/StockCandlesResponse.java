package by.itechart.model.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "c",
        "h",
        "l",
        "o",
        "s",
        "t",
        "v"
})
@Data
public class StockCandlesResponse {

    @JsonProperty("c")
    private List<Double> currentPrices = null;
    @JsonProperty("h")
    private List<Double> highPrices = null;
    @JsonProperty("l")
    private List<Double> lowPrices = null;
    @JsonProperty("o")
    private List<Double> openPrices = null;
    @JsonProperty("s")
    private String statusResponse;
    @JsonProperty("t")
    private List<Integer> timestamps = null;
    @JsonProperty("v")
    private List<Integer> volume = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}