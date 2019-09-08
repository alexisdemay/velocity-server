package fr.velocity.repository;

import fr.velocity.document.Station;
import java.util.List;

public interface StationsRepository {

    void insert(Station station);

    void insertList(List<Station> listOfStatistics);

}
