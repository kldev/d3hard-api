package com.kldev.d3.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateHeroRankRequest extends BaseRequest {


    @JsonProperty
    private String rankType;

    @JsonProperty
    private int season;

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
