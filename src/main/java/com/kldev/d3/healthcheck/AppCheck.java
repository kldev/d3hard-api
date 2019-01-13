package com.kldev.d3.healthcheck;

import com.codahale.metrics.health.HealthCheck;

public class AppCheck extends HealthCheck {

    private final String version;

    public AppCheck(String version) {
        this.version = version;
    }


    @Override
    protected Result check() throws Exception {

        return Result.healthy("OK - version " + version);
    }
}
