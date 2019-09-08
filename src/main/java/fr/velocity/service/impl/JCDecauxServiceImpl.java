package fr.velocity.service.impl;

import fr.velocity.client.JCDecauxClient;
import fr.velocity.model.Contract;
import fr.velocity.model.Station;
import fr.velocity.model.StatsStations;
import fr.velocity.service.JCDecauxService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JCDecauxServiceImpl implements JCDecauxService {

    @Autowired
    private JCDecauxClient jcDecauxClient;

    @Override
    public Flux<Contract> listContracts() {
        return jcDecauxClient.listContracts();
    }

    @Override
    public Flux<Station> listStations(Optional<String> contractName) {
        return contractName.isPresent() ? jcDecauxClient.listStationsByContract(contractName.get())
                : jcDecauxClient.listStations();
    }

    @Override
    public Mono<Long> countStations(Optional<String> contractName) {
        return contractName.isPresent() ? jcDecauxClient.listStationsByContract(contractName.get())
                .count() : jcDecauxClient.listStations().count();
    }

    @Override
    public List<StatsStations> statsStations(Optional<String> contractName) {

        Flux<Station> stations =
                contractName.isPresent() ? listStations(Optional.of(contractName.get()))
                        : listStations(Optional.empty());
        Flux<Contract> listOfContracts = listContracts();

        List<StatsStations> stats = new ArrayList<>();

        if (contractName.isPresent() && !contractName.get().equals("*")) {
            Contract contract = listOfContracts
                    .toStream()
                    .filter(c -> contractName.get().equals(c.getName()))
                    .findFirst()
                    .orElse(Contract.unknown());
            stats.add(
                    StatsStations.generateStatsFromStationsAndContract(stations.toStream().collect(
                            Collectors.toList()), contract));
        } else {
            stations
                    .toStream()
                    .filter(station -> station.getContractName() != null)
                    .collect(Collectors.groupingBy(Station::getContractName))
                    .forEach((cn, listOfStations) -> {
                        Contract contract = listOfContracts
                                .toStream()
                                .filter(c -> cn.equals(c.getName()))
                                .findFirst()
                                .orElse(Contract.unknown());
                        stats.add(StatsStations.generateStatsFromStationsAndContract(listOfStations, contract));
                    });
        }

        return stats;

    }

}
