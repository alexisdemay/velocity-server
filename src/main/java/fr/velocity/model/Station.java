package fr.velocity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.velocity.deserializer.TimestampDeserialization;
import fr.velocity.serializer.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Station {

    @JsonProperty("number")
    private Integer number;

    @JsonProperty("contract_name")
    private String contractName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("position")
    private Position position;

    @JsonProperty("banking")
    private Boolean banking;

    @JsonProperty("bonus")
    private Boolean bonus;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("bike_stands")
    private Integer bikeStands;

    @JsonProperty("available_bike_stands")
    private Integer availableBikesStands;

    @JsonProperty("available_bikes")
    private Integer availableBikes;

    @JsonProperty("last_update")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserialization.class)
    private LocalDateTime lastUpdate;

}
