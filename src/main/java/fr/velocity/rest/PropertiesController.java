package fr.velocity.rest;

import fr.velocity.config.AppDefaultProperties;
import fr.velocity.config.AppDefaultProperties.Tasks;
import fr.velocity.config.AppDefaultProperties.Tasks.Stations;
import fr.velocity.config.AppDefaultProperties.Tasks.Statistics;
import fr.velocity.config.AppDynamicProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PropertiesController {

    @Autowired
    private AppDynamicProperties appDynamicProperties;

    @GetMapping("/tasks")
    public Tasks getTasksProperties() {
        return appDynamicProperties.getTasks();
    }

    @GetMapping("/tasks/stations")
    public Stations enabledOrDisabledStationsTask(@RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) Long fixedRate) {
        if (enabled != null) {
            appDynamicProperties.getTasks().getStations().setEnabled(enabled);
        }
        if (fixedRate != null && fixedRate >= AppDefaultProperties.FIXED_RATE_MIN_VALUE) {
            appDynamicProperties.getTasks().getStations().setFixedRate(fixedRate);
        }
        return appDynamicProperties.getTasks().getStations();
    }

    @GetMapping("/tasks/statistics")
    public Statistics disabledStatisticsTask(@RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) Long fixedRate) {
        if (enabled != null) {
            appDynamicProperties.getTasks().getStatistics().setEnabled(enabled);
        }
        if (fixedRate != null && fixedRate >= AppDefaultProperties.FIXED_RATE_MIN_VALUE) {
            appDynamicProperties.getTasks().getStatistics().setFixedRate(fixedRate);
        }
        return appDynamicProperties.getTasks().getStatistics();
    }

}
