package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActIndexResponse {

    @JsonProperty("acts")
    private ActList acts;

    public ActList getActs() {
        return acts;
    }

    public void setActs(ActList acts) {
        this.acts = acts;
    }
}
