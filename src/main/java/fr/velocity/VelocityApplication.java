package fr.velocity;

import fr.velocity.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"fr.velocity.repository"})
public class VelocityApplication {

	public static void main(String[] args) {
		SpringApplication.run(VelocityApplication.class, args);
	}

}
