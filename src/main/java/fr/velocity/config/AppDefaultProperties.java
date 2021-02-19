package fr.velocity.config;

import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@Getter
@ConfigurationProperties(prefix = "app")
public class AppDefaultProperties {

    public static final Long FIXED_RATE_MIN_VALUE = 5000L;

    private final JCDecaux jcdecaux = new JCDecaux();
    private final Tasks tasks = new Tasks();

    @PostConstruct
    public void print() {
        log.info("Application default properties has been loaded");
    }

    @Data
    public static class JCDecaux {
        private String baseUrl;
        private String apiKey;
    }

    @Data
    public static class Tasks {
        private Stations stations;
        private Statistics statistics;
        private long defaultFixedRate;
        @Data
        public static class Stations {

            private boolean enabled;
            private long fixedRate;
        }
        @Data
        public static class Statistics {
            private boolean enabled;
            private long fixedRate;
        }
    }

}
