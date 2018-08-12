package fr.velocity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "stats_stations", schema = "velocity")
public class StatsStations {

    @Id
    @SequenceGenerator(name = "sequence_stats_stations", sequenceName = "velocity.stats_stations_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "sequence_stats_stations")
    @Column(name = "id")
    private Long id;

    @Column(name = "contract_name", nullable = false)
    private String contract;

    @Column(name = "total_stations", nullable = false)
    private Integer totalStations;

    @Column(name = "total_open_stations", nullable = false)
    private Integer totalOpenStations;

    @Column(name = "total_closed_stations", nullable = false)
    private Integer totalClosedStations;

    @Column(name = "total_bikes", nullable = false)
    private Integer totalBikes;

    @Column(name = "total_available_bike", nullable = false)
    private Integer totalAvailableBike;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public StatsStations(fr.velocity.model.StatsStations statsStations) {
        this.contract = statsStations.getContract();
        this.totalStations = statsStations.getTotalStations();
        this.totalOpenStations = statsStations.getTotalOpenStations();
        this.totalClosedStations = statsStations.getTotalClosedStations();
        this.totalBikes = statsStations.getTotalBikes();
        this.totalAvailableBike = statsStations.getTotalAvailableBike();
        this.createdDate = LocalDateTime.now();
    }

}
