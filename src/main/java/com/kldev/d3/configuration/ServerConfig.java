package com.kldev.d3.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ServerConfig extends Configuration {

    @NotEmpty
    private String version;

    @JsonProperty
    public String getVersion() {
        return version;
    }

    @NotNull
    @JsonProperty
    private BlizardConfig blizard;


    @JsonProperty
    public void setVersion(String version) {
        this.version = version;
    }

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public BlizardConfig getBlizard() {
        return blizard;
    }



    @Override
    public String toString() {
        return "ServerConfig{" +
                "version='" + version + '\'' +
                ", blizard=" + blizard +
                ", database=" + database +
                '}';
    }


}
