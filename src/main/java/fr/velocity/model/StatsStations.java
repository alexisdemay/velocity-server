package fr.velocity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatsStations {

    @JsonProperty("contract_name")
    private String contract;

    @JsonProperty("total_stations")
    private Integer totalStations;

    @JsonProperty("total_open_stations")
    private Integer totalOpenStations;

    @JsonProperty("total_closed_stations")
    private Integer totalClosedStations;

    @JsonProperty("total_bikes")
    private Integer totalBikes;

    @JsonProperty("total_available_bike")
    private Integer totalAvailableBike;

}
