package com.heroxu.cases.flyway;

import org.flywaydb.core.Flyway;
import org.junit.Test;

public class FlywayTest {

    @Test
    public void test(){
        // Create the Flyway instance
        Flyway flyway = new Flyway();

        // Point it to the database
        flyway.setDataSource("jdbc:mysql://127.0.0.1:3306/flyway?useLegacyDatetimeCode=false&serverTimezone=Asia/Hong_Kong&useSSL=false", "root", "1234");
        flyway.baseline();
        // Start the migration
        flyway.migrate();
    }

}
