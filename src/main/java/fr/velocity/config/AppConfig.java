package fr.velocity.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public static final String PROFIL_DEV = "dev";

    public static final String PROFIL_PROD = "prod";

    public static final String PROFIL_DOCKER = "docker";

    @Autowired
    private AppDefaultProperties appDefaultProperties;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AppDynamicProperties appDynamicProperties() {
        return new AppDynamicProperties(appDefaultProperties);
    }

}
