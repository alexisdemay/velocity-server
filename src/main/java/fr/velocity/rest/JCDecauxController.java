package fr.velocity.rest;

import fr.velocity.model.Contract;
import fr.velocity.model.Station;
import fr.velocity.model.StatsStations;
import fr.velocity.service.JCDecauxService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class JCDecauxController {

    @Autowired
    private JCDecauxService jcDecauxService;

    @GetMapping("/contracts")
    public Flux<Contract> listContrats() {
        return jcDecauxService.listContracts();
    }

    @GetMapping("/stations")
    public Flux<Station> listStations(@RequestParam(required = false) String contract) {
        return contract != null ? jcDecauxService.listStations(Optional.of(contract))
                : jcDecauxService.listStations(Optional.empty());
    }

    @GetMapping("/stations/count")
    public Mono<Long> countStations(@RequestParam(required = false) String contract) {
        return contract != null ? jcDecauxService.countStations(Optional.of(contract))
                : jcDecauxService.countStations(Optional.empty());
    }

    @GetMapping("/stations/stats")
    public List<StatsStations> statsStations(@RequestParam(required = false) String contract) {
        return contract != null ? jcDecauxService.statsStations(Optional.of(contract))
                : jcDecauxService.statsStations(Optional.empty());
    }

}
