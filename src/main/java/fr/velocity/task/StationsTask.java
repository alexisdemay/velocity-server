package fr.velocity.task;

import fr.velocity.document.Station;
import fr.velocity.repository.StationsRepository;
import fr.velocity.service.JCDecauxService;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StationsTask {

    @Autowired
    private JCDecauxService jcdecauxService;

    @Autowired
    private StationsRepository stationsRepository;

    @Autowired
    private ModelMapper mapper;

    @Scheduled(fixedRate = 15000)
    public void retrieveStationsTask() {
        log.info("Retrieve stations at {}", Instant.now().toString());
        List<Station> listOfStations = jcdecauxService
                .listStations(Optional.empty())
                .toStream()
                .filter(Objects::nonNull)
                .map(s -> mapper.map(s, Station.class))
                .collect(Collectors.toList());
        stationsRepository.insertList(listOfStations);
    }

}
