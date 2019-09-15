package fr.velocity.config;

import fr.velocity.task.StationsTask;
import fr.velocity.task.StatisticsTask;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.PeriodicTrigger;

@Slf4j
@Configuration
public class AppScheduling implements SchedulingConfigurer {

    @Autowired
    private StationsTask stationsTask;

    @Autowired
    private StatisticsTask statisticsTask;

    @Autowired
    private AppDynamicProperties appDynamicProperties;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        log.info("Adding {} to the scheduled tasks", stationsTask.getClass().getSimpleName());
        scheduledTaskRegistrar.addTriggerTask(
                () -> stationsTask.retrieveStations(),
                triggerContext -> getTriggerContext(
                        triggerContext,
                        appDynamicProperties.getTasks().getStations().getFixedRate(),
                        stationsTask.getClass().getSimpleName()
                ));
        log.info("Adding {} to the scheduled tasks", statisticsTask.getClass().getSimpleName());
        scheduledTaskRegistrar.addTriggerTask(
                () -> statisticsTask.retrieveStatistics(),
                triggerContext -> getTriggerContext(
                        triggerContext,
                        appDynamicProperties.getTasks().getStatistics().getFixedRate(),
                        statisticsTask.getClass().getSimpleName()
                ));
    }

    private Date getTriggerContext(TriggerContext triggerContext, Long fixedRate, String taskName) {
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(fixedRate);
        Date nextExec = periodicTrigger.nextExecutionTime(triggerContext);
        log.info("Next execution of {} is scheduled at {}", taskName, nextExec.toString());
        return nextExec;
    }

}
