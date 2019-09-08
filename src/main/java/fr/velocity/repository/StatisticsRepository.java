package fr.velocity.repository;

import fr.velocity.document.Statistics;
import java.util.List;

public interface StatisticsRepository {

    void insert(Statistics statsStations);

    void insertList(List<Statistics> listOfStatistics);

}
