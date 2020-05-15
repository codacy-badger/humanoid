package de.cloudbug.humanoid.profile.health;

import de.cloudbug.humanoid.profile.Profile;
import de.cloudbug.humanoid.profile.ProfileService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@Readiness
@ApplicationScoped
public class DatabaseConnectionHealthCheck implements HealthCheck {

    @Inject
    ProfileService profileService;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse
                .named("Profile: Datasource connection health check");

        try {
            List<Profile> heroes = profileService.findAllProfiles();
            responseBuilder.withData("Number of profiles in the database", heroes.size()).up();
        } catch (IllegalStateException e) {
            responseBuilder.down();
        }

        return responseBuilder.build();
    }
}