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

    @JsonProperty("commercialName")
    private String commercialName;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("cities")
    private List<String> cities;

}