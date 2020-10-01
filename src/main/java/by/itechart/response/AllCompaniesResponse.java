
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
        "currency",
        "description",
        "displaySymbol",
        "symbol",
        "type"
})
@Data
public class AllCompaniesResponse {

    @JsonProperty("currency")
    private String currency;
    @JsonProperty("description")
    private String description;
    @JsonProperty("displaySymbol")
    private String displaySymbol;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();
}