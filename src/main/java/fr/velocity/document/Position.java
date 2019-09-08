package fr.velocity.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Position {

    @JsonProperty("lat")
    private Float latitude;

    @JsonProperty("lon")
    private Float longitude;

}
