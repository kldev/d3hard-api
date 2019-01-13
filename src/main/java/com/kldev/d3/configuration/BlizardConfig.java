package com.kldev.d3.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class BlizardConfig {

    @NotEmpty
    @JsonProperty
    private String clientId;

    @NotEmpty
    @JsonProperty
    private String clientSecret;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }


    @Override
    public String toString() {
        return "BlizardConfig{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }
}
