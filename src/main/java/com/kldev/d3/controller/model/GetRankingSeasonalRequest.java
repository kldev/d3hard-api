package com.kldev.d3.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetRankingSeasonalRequest extends GetRankingRequest {

    @JsonProperty
    private int season;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
