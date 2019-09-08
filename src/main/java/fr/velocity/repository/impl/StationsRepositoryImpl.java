package fr.velocity.repository.impl;

import fr.velocity.document.Station;
import fr.velocity.repository.ElasticApiManager;
import fr.velocity.repository.StationsRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class StationsRepositoryImpl implements StationsRepository {

    @Autowired
    private ElasticApiManager elasticApiManager;

    @Override
    public void insert(Station station) {
        log.debug("Inserting the following document: {}", station.toString());
        elasticApiManager.indexAsync(station);
    }

    @Override
    public void insertList(List<Station> listOfStations) {
        if (CollectionUtils.isNotEmpty(listOfStations)) {
            log.debug("Inserting {} documents", listOfStations.size());
            elasticApiManager.bulkAsync(listOfStations);
        } else {
            log.debug("Inserting 0 document");
        }
    }

}
