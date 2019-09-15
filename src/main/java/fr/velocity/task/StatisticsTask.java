package fr.velocity.task;

import fr.velocity.config.AppDynamicProperties;
import fr.velocity.document.Statistics;
import fr.velocity.repository.StatisticsRepository;
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
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StatisticsTask {

    @Autowired
    private JCDecauxService jcdecauxService;

    @Autowired
    private StatisticsRepository statsStationsRepository;

    @Autowired
    private AppDynamicProperties appDynamicProperties;

    @Autowired
    private ModelMapper mapper;

    public void retrieveStatistics() {
        if (appDynamicProperties != null
                && appDynamicProperties.getTasks() != null
                && appDynamicProperties.getTasks().getStatistics().isEnabled()) {
            log.info("Retrieve statistics at {}", Instant.now().toString());
            List<Statistics> listOfStatistics = jcdecauxService
                    .statsStations(Optional.empty())
                    .stream()
                    .filter(Objects::nonNull)
                    .map(s -> mapper.map(s, Statistics.class))
                    .collect(Collectors.toList());
            statsStationsRepository.insertList(listOfStatistics);
        }
    }

}
