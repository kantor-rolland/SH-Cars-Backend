package edu.bbte.idde.krim2244.dataaccess.jdbc;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class HikariConnection {

    private static HikariDataSource dataSource;

    public static synchronized DataSource buildDataSource() {

        if (dataSource == null) {
            dataSource = new HikariDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/sh_cars");
            dataSource.setUsername("sh_user");
            dataSource.setPassword("password");
            dataSource.setMaximumPoolSize(4);
        }
        return dataSource;
    }
}
