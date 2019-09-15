package fr.velocity.config;

import fr.velocity.config.AppDefaultProperties.JCDecaux;
import fr.velocity.config.AppDefaultProperties.Tasks;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AppDynamicProperties {

    private JCDecaux jcdecaux;
    private Tasks tasks;

    private AppDynamicProperties() {
        // Private constructor
    }

    public AppDynamicProperties(AppDefaultProperties appDefaultProperties) {
        this.jcdecaux = appDefaultProperties.getJcdecaux();
        this.tasks = appDefaultProperties.getTasks();
    }

    @PostConstruct
    public void print() {
        log.info("Application dynamic properties has been loaded");
    }

}
