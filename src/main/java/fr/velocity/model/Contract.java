package fr.velocity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contract {

    @JsonProperty("name")
    private String name;

    @JsonProperty("commercial_name")
    private String commercialName;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("cities")
    private List<String> cities;

    public static Contract unknown() {
        return new Contract("unknown", "unknown", "unknown", null);
    }

}