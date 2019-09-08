package fr.velocity.service;

import fr.velocity.model.Contract;
import fr.velocity.model.Station;
import fr.velocity.model.StatsStations;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface JCDecauxService {

    Flux<Contract> listContracts();

    Flux<Station> listStations(Optional<String> contractName);

    Mono<Long> countStations(Optional<String> contractName);

    List<StatsStations> statsStations(Optional<String> contractName);

}
