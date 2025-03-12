package edu.bbte.idde.krim2244.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurationFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationFactory.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static MainConfiguration mainConfiguration;

    private static String configFile() {
        StringBuilder sb = new StringBuilder();

        // profil kiolvasasa a kornyezeti valtozobol
        String profile = System.getenv("PROFILE");

        if (profile == null) {
            profile = "dev";
        }

        if (profile != null) {
            sb.append("/config_").append(profile).append(".json");
        }

        return sb.toString();
    }

    static {
        String configFile = configFile();

        try (InputStream inputStream = ConfigurationFactory.class.getResourceAsStream(configFile)) {
            mainConfiguration = objectMapper.readValue(inputStream, MainConfiguration.class);
            LOG.info("Read following configuration: {}", mainConfiguration);
        } catch (IOException e) {
            LOG.error("Error loading configuration", e);
        }
    }

    public static MainConfiguration getMainConfiguration() {
        return mainConfiguration;
    }

    public static JdbcConfiguration getJdbcConfiguration() {
        return mainConfiguration.getJdbcConfiguration();
    }

    public static ConnectionPoolConfiguration getConnectionPoolConfiguration() {
        return mainConfiguration.getConnectionPoolConfiguration();
    }
}
