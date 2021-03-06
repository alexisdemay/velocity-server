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

    private final StationsTask stationsTask;

    private final StatisticsTask statisticsTask;

    private final AppDefaultProperties appDefaultProperties;

    private final AppDynamicProperties appDynamicProperties;

    @Autowired
    public AppScheduling(StationsTask stationsTask, StatisticsTask statisticsTask, AppDefaultProperties appDefaultProperties, AppDynamicProperties appDynamicProperties) {
        this.stationsTask = stationsTask;
        this.statisticsTask = statisticsTask;
        this.appDefaultProperties = appDefaultProperties;
        this.appDynamicProperties = appDynamicProperties;
    }

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
        boolean isEnableTask = false;
        if (appDynamicProperties != null && appDynamicProperties.getTasks() != null) {
            if (taskName == StationsTask.class.getSimpleName()) {
                isEnableTask = appDynamicProperties.getTasks().getStations().isEnabled();
            } else if (taskName == StatisticsTask.class.getSimpleName()) {
                isEnableTask = appDynamicProperties.getTasks().getStatistics().isEnabled();
            }
        }
        Date nextExec;
        if (isEnableTask) {
            PeriodicTrigger periodicTrigger = new PeriodicTrigger(fixedRate);
            nextExec = periodicTrigger.nextExecutionTime(triggerContext);
            log.info("Next execution of {} is scheduled at {}", taskName, nextExec.toString());
        } else {
            PeriodicTrigger periodicTrigger = new PeriodicTrigger(appDefaultProperties.getTasks().getDefaultFixedRate());
            nextExec = periodicTrigger.nextExecutionTime(triggerContext);
            log.warn("{} is disabled and the next execution is scheduled at {}", taskName, nextExec.toString());
        }
        return nextExec;
    }

}
