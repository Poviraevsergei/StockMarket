package by.itechart.model.response;

import java.util.Map;
import java.util.HashMap;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyStockForTheYearResponse {

    @JsonProperty("metricType")
    private String metricType;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("metric")
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}