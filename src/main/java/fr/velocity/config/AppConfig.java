package fr.velocity.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public static final String PROFIL_DEV = "dev";

    public static final String PROFIL_PROD = "prod";

    public static final String PROFIL_DOCKER = "docker";

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
