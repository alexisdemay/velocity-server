package fr.velocity.service.impl;

import fr.velocity.client.JCDecauxClient;
import fr.velocity.model.Contract;
import fr.velocity.model.Station;
import fr.velocity.model.StatsStations;
import fr.velocity.model.Status;
import fr.velocity.repository.StatsStationsRepository;
import fr.velocity.service.JCDecauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class JCDecauxServiceImpl implements JCDecauService {

    @Autowired
    private JCDecauxClient jcDecauxClient;

    @Autowired
    private StatsStationsRepository statsStationsRepository;

    @Override
    public Flux<Contract> listContracts() {
        return jcDecauxClient.listContracts();
    }

    @Override
    public Flux<Station> listStations(Optional<String> contractName) {
        return contractName.isPresent() ? jcDecauxClient.listStationsByContract(contractName.get()) : jcDecauxClient.listStations();
    }

    @Override
    public Mono<Long> countStations(Optional<String> contractName) {
        return contractName.isPresent() ? jcDecauxClient.listStationsByContract(contractName.get()).count() : jcDecauxClient.listStations().count();
    }

    @Override
    public Mono<StatsStations> statsStations(Optional<String> contractName) {

        Flux<Station> stations = contractName.isPresent() ? listStations(Optional.of(contractName.get())) : listStations(Optional.empty());

        return stations.collectList().map(listStations -> {

            StatsStations stats;

            if (contractName.isPresent()) {
                stats = new StatsStations(contractName.get(), 0, 0, 0, 0, 0);
            } else {
                stats = new StatsStations("all", 0, 0, 0, 0, 0);
            }

            listStations.stream().forEach(station -> {

                stats.setTotalBikes(station.getBikeStands() + stats.getTotalBikes());
                stats.setTotalAvailableBike(station.getAvailableBikes() + stats.getTotalAvailableBike());
                stats.setTotalStations(stats.getTotalStations() + 1);

                if (Status.OPEN.equals(station.getStatus())) {
                    stats.setTotalOpenStations(stats.getTotalOpenStations() + 1);
                } else {
                    stats.setTotalClosedStations(stats.getTotalClosedStations() + 1);
                }

            });

            statsStationsRepository.save(new fr.velocity.entity.StatsStations(stats));

            return stats;

        });

    }

}
