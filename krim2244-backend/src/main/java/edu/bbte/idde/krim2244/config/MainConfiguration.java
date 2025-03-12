package edu.bbte.idde.krim2244.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Általános konfigurációs bean az alkalmazásunknak
 * További modul-specifikus konfigurációs beaneket tartalmazhat
 */

public class  MainConfiguration {
    // opcionális metainformáció - a JSON-ben más a név mint az adattag neve
    @JsonProperty("jdbc")
    private final JdbcConfiguration jdbcConfiguration = new JdbcConfiguration();

    @JsonProperty("connectionPool")
    private final ConnectionPoolConfiguration connectionPoolConfiguration = new ConnectionPoolConfiguration();

    @JsonProperty("dbType")
    private String dbType;

    public JdbcConfiguration getJdbcConfiguration() {
        return jdbcConfiguration;
    }

    public ConnectionPoolConfiguration getConnectionPoolConfiguration() {
        return connectionPoolConfiguration;
    }

    public String getDbType() {
        return dbType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MainConfiguration{");
        sb.append("jdbcConfiguration=").append(jdbcConfiguration);
        sb.append(", connectionPoolConfiguration=").append(connectionPoolConfiguration);
        sb.append('}');
        return sb.toString();
    }
}