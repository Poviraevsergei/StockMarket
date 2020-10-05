package by.itechart.model.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "category",
        "datetime",
        "headline",
        "id",
        "image",
        "related",
        "source",
        "summary",
        "url"
})
@Data
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
}