package fr.velocity.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final JCDecaux jcdecaux = new JCDecaux();

    @Data
    public static class JCDecaux {

        private String baseUrl;

        private String apiKey;

    }

}
