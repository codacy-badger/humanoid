package de.cloudbug.humanoid;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.GenericContainer;

import java.util.Collections;
import java.util.Map;

public class DatabaseResource implements QuarkusTestResourceLifecycleManager {

    private static final GenericContainer DATABASE = new GenericContainer<>("mongo:4.0")
            .withExposedPorts(27017);

    @Override
    public Map<String, String> start() {
        DATABASE.start();
        return Collections.singletonMap("quarkus.mongodb.connection-string",
                "mongodb://" + DATABASE.getContainerIpAddress() + ":" + DATABASE.getFirstMappedPort()
        );
    }

    @Override
    public void stop() {
        DATABASE.stop();
    }
}
