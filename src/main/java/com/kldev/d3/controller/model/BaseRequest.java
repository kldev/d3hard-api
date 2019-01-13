package com.kldev.d3.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseRequest {

    @JsonProperty
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
