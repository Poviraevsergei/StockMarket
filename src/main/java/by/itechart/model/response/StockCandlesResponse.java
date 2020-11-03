package by.itechart.model.response;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockCandlesResponse {

    @JsonProperty("c")
    private List<Double> currentPrices;

    @JsonProperty("h")
    private List<Double> highPrices;

    @JsonProperty("l")
    private List<Double> lowPrices;

    @JsonProperty("o")
    private List<Double> openPrices;

    @JsonProperty("s")
    private String statusResponse;

    @JsonProperty("t")
    private List<Integer> timestamps;

    @JsonProperty("v")
    private List<Integer> volume;

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