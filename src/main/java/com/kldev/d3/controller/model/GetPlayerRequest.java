package com.kldev.d3.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetPlayerRequest extends BaseRequest {

    @JsonProperty
    private String bTag;

    public String getbTag() {
        return bTag;
    }

    public void setbTag(String bTag) {
        this.bTag = bTag;
    }
}
