package com.example.proyectodbp.Test.station.Infrasctructure;

import org.testcontainers.containers.PostgreSQLContainer;

public class StationTestContainer extends PostgreSQLContainer<StationTestContainer> {
    private static final String IMAGE_VERSION = "postgres:13.3";
    private static StationTestContainer container;

    private StationTestContainer() {
        super(IMAGE_VERSION);
    }

    public static StationTestContainer getInstance() {
        if (container == null) {
            container = new StationTestContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        // Do nothing, JVM handles shut down
    }
}