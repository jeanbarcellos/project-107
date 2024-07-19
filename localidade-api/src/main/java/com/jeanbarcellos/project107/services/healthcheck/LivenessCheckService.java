package com.jeanbarcellos.project107.services.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class LivenessCheckService implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("O microserviço está online").up().build();
    }

}
