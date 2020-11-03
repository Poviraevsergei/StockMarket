package by.itechart.model.response;

import java.util.Map;
import java.util.HashMap;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyNewsResponse {

    @JsonProperty("category")
    private String category;

    @JsonProperty("datetime")
    private Integer datetime;

    @JsonProperty("headline")
    private String headline;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("image")
    private String image;

    @JsonProperty("related")
    private String related;

    @JsonProperty("source")
    private String source;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("url")
    private String url;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}