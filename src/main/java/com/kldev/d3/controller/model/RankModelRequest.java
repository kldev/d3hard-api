package com.kldev.d3.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RankModelRequest extends BaseRequest {

    @JsonProperty
    private int offset;

    @JsonProperty
    private String btag;

    @JsonProperty
    private String orderBy = OrderByType.position.name();

    @JsonProperty
    private String direction = "desc";


    @JsonProperty
    private String rank = RankType.player.name();

    @JsonProperty
    private String hero = HeroType.barbarian.name();

    @JsonProperty("seasonal")
    private String seasonal;

    @JsonProperty("hardcore")
    private String hardcore;

    @JsonProperty
    private int season;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getBtag() {
        return btag;
    }

    public void setBtag(String btag) {
        this.btag = btag;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getSeasonal() {
        return seasonal;
    }

    public void setSeasonal(String seasonal) {
        this.seasonal = seasonal;
    }

    public String getHardcore() {
        return hardcore;
    }

    public void setHardcore(String hardcore) {
        this.hardcore = hardcore;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
