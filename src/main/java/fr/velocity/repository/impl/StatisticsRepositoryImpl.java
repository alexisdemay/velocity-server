package fr.velocity.repository.impl;

import fr.velocity.document.Statistics;
import fr.velocity.repository.ElasticApiManager;
import fr.velocity.repository.StatisticsRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {

    @Autowired
    private ElasticApiManager elasticApiManager;

    public void insert(Statistics statistics) {
        log.debug("Inserting the following document: {}", statistics.toString());
        elasticApiManager.indexAsync(statistics);
    }

    public void insertList(List<Statistics> listOfStatistics) {
        if (CollectionUtils.isNotEmpty(listOfStatistics)) {
            log.debug("Inserting {} documents", listOfStatistics.size());
            elasticApiManager.bulkAsync(listOfStatistics);
        } else {
            log.debug("Inserting 0 document");
        }
    }

}
