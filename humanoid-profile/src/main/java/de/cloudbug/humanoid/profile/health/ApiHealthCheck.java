package de.cloudbug.humanoid.profile.health;

import de.cloudbug.humanoid.profile.ProfileApi;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Liveness
@ApplicationScoped
public class ApiHealthCheck implements HealthCheck {

    @Inject
    ProfileApi profileApi;

    @Override
    public HealthCheckResponse call() {
        profileApi.healthCheck();
        return HealthCheckResponse.named("Profile-REST-Endpoint").up().build();
    }
}
