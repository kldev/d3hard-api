package com.kldev.d3.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSeasonConfigRequest extends BaseRequest {

    @JsonProperty
    private RankType rankType;

    public RankType getRankType() {
        return rankType;
    }
}
