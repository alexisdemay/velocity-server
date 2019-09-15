package fr.velocity;

import fr.velocity.config.AppDefaultProperties;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication(exclude = RestClientAutoConfiguration.class)
@EnableScheduling
@EnableConfigurationProperties(AppDefaultProperties.class)
public class VelocityApplication {

    private static final String PID_FILE_PATH = "pid_file_path";

    public static void main(String[] args) {

        SpringApplication velocityApp = new SpringApplication(VelocityApplication.class);
        Map<String, Object> parameters = getArgs(args);

        File pidFile = parameters.get(PID_FILE_PATH) != null && parameters.get(PID_FILE_PATH) instanceof File ? (File) parameters.get(PID_FILE_PATH) : null;

        if (pidFile != null) {
            velocityApp.addListeners(new ApplicationPidFileWriter(pidFile));
        }

        velocityApp.run(args);

    }

    private static Map<String, Object> getArgs(String[] args) {

        Map<String, Object> argsMap = new HashMap<>();
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        Option pathPidFileOpt = new Option("p", "pid-file", true, "path to the pid file");

        options.addOption(pathPidFileOpt);

        CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            log.error("An error has occurred while parsing application arguments", e);
            formatter.printHelp("velocity-app", options);
            throw new RuntimeException();
        }

        String pathPidFile = commandLine.getOptionValue("pid-file");

        if (StringUtils.isNotBlank(pathPidFile)) {
            argsMap.put(PID_FILE_PATH, new File(pathPidFile));
        }

        return argsMap;

    }

}
