package fr.velocity.rest;

import fr.velocity.model.Contract;
import fr.velocity.model.Station;
import fr.velocity.model.StatsStations;
import fr.velocity.service.JCDecauxService;
import io.prometheus.client.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class JCDecauxController {

    @Autowired
    private JCDecauxService jcDecauxService;

    private Counter counterStatsStationRequest = Counter
            .build()
            .help("Number of request made to retrieve station statistics.")
            .name("counter_stats_stations_request")
            .register();

    @GetMapping("/contracts")
    public Flux<Contract> listContrats() {
        return jcDecauxService.listContracts();
    }

    @GetMapping("/stations")
    public Flux<Station> listStations(@RequestParam(required = false) String contract) {
        return contract != null ? jcDecauxService.listStations(Optional.of(contract)) : jcDecauxService.listStations(Optional.empty());
    }

    @GetMapping("/stations/count")
    public Mono<Long> countStations(@RequestParam(required = false) String contract) {
        return contract != null ? jcDecauxService.countStations(Optional.of(contract)) : jcDecauxService.countStations(Optional.empty());
    }

    @GetMapping("/stations/stats")
    public Mono<StatsStations> statsStations(@RequestParam(required = false) String contract) {
        counterStatsStationRequest.inc();
        return contract != null ? jcDecauxService.statsStations(Optional.of(contract)) : jcDecauxService.statsStations(Optional.empty());
    }

}
