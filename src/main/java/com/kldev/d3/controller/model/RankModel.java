package com.kldev.d3.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RankModel {

    @JsonProperty
    private String id;

    @JsonProperty
    private String btag;

    @JsonProperty
    private String forum;

    @JsonProperty
    private String guild;

    @JsonProperty
    private int rank;

    @JsonProperty
    private int position;

    @JsonProperty
    private int postionChange;

    @JsonProperty
    private String updateDate;

    @JsonProperty
    private int season;

    @JsonProperty
    private int rankEu;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBtag() {
        return btag;
    }

    public void setBtag(String btag) {
        this.btag = btag;
    }

    public String getForum() {
        return forum;
    }

    public void setForum(String forum) {
        this.forum = forum;
    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getPostionChange() {
        return postionChange;
    }

    public void setPostionChange(int postionChange) {
        this.postionChange = postionChange;
    }

    public int getRankEu() {
        return rankEu;
    }

    public void setRankEu(int rankEu) {
        this.rankEu = rankEu;
    }
}
