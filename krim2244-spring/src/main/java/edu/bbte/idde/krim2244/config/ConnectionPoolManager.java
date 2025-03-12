package edu.bbte.idde.krim2244.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("!dev")
public class ConnectionPoolManager {
    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username:root}")
    private String username;

    @Value("${jdbc.password:root}")
    private String password;

    @Value("${jdbc.driver-class-name}")
    private String driverClassName;

    @Value("${connection-pool.pool-size:5}")
    private Integer poolSize;

    @Bean
    public DataSource dataSource() {
        HikariConfig dataSource = new HikariConfig();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(poolSize);

        return new HikariDataSource(dataSource);
    }
}