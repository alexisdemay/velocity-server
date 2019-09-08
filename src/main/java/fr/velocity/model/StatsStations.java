package fr.velocity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.velocity.deserializer.TimestampDeserialization;
import fr.velocity.serializer.LocalDateTimeSerializer;
import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatsStations {

    @JsonProperty("contract")
    private Contract contract;

    @JsonProperty("total_stations")
    private Integer totalStations = 0;

    @JsonProperty("total_open_stations")
    private Integer totalOpenStations = 0;

    @JsonProperty("total_closed_stations")
    private Integer totalClosedStations = 0;

    @JsonProperty("total_bikes_capacity")
    private Integer totalBikesCapacity = 0;

    @JsonProperty("total_bikes_available")
    private Integer totalBikesAvailable = 0;

    @JsonProperty("total_bikes_used")
    private Integer totalBikesUsed = 0;

    @JsonProperty("total_stands_available")
    private Integer totalStandsAvailable = 0;

    @JsonProperty("last_update")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserialization.class)
    private Instant lastUpdate = Instant.now();

    public StatsStations(Contract contract) {
        this.contract = contract;
    }

    public static StatsStations generateStatsFromStationsAndContract(List<Station> listOfStations,
            Contract contract) {
        StatsStations stats = new StatsStations(contract);
        listOfStations.forEach(station -> {
            stats.addStation(station.getStatus());
            stats.increaseBikesCapacity(station.getMainStands().getCapacity());
            stats.increaseBikesAvailable(
                    station.getMainStands().getAvailabilities().getBikes());
            stats.increaseStandsAvailable(
                    station.getMainStands().getAvailabilities().getStands());
            stats.updateBikesUsed();
        });
        return stats;
    }

    public void addStation(Status status) {
        this.totalStations += 1;
        if (Status.OPEN.equals(status)) {
            this.totalOpenStations += 1;
        } else {
            this.totalClosedStations += 1;
        }
    }

    public void increaseBikesCapacity(Integer capacity) {
        this.totalBikesCapacity += capacity;
    }

    public void increaseBikesAvailable(Integer availableBikes) {
        this.totalBikesAvailable += availableBikes;
    }

    public void increaseStandsAvailable(Integer availableStands) {
        this.totalStandsAvailable += availableStands;
    }

    public void updateBikesUsed() {
        this.totalBikesUsed = totalBikesCapacity - totalStandsAvailable;
    }

}
