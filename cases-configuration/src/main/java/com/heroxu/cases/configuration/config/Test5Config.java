package com.heroxu.cases.configuration.config;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class Test5Config {

    public static String name;
    public static int id;

    private static String property = "test5.properties";

    private static Test5Config myConfig;

    static {
        myConfig = loadConfig();
    }

    private static Test5Config loadConfig() {
        if (myConfig == null) {
            myConfig = new Test5Config();
            Properties properties;
            try {
                properties = PropertiesLoaderUtils.loadAllProperties(property);

                name = properties.getProperty("test5.name");
                id = Integer.valueOf(properties.getProperty("test5.id"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return myConfig;
    }

    public Test5Config getInstance() {
        return myConfig;
    }

}
