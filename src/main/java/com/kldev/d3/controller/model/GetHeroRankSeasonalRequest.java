package com.kldev.d3.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetHeroRankSeasonalRequest extends GetHeroRankRequest {

    @JsonProperty
    private int season;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

}
