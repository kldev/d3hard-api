package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Season {

    @JsonProperty("seasonId")
    private int seasonId;

    @JsonProperty("paragonLevel")
    private int paragonLevel;

    @JsonProperty("paragonLevelHardcore")
    private int paragonLevelHardcore;

    @JsonProperty("kills")
    private HeroKill kills;

    @JsonProperty("timePlayed")
    private TimePlayed timePlayed;

    @JsonProperty("highestHardcoreLevel")
    private int highestHardcoreLevel;

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getParagonLevel() {
        return paragonLevel;
    }

    public void setParagonLevel(int paragonLevel) {
        this.paragonLevel = paragonLevel;
    }

    public int getParagonLevelHardcore() {
        return paragonLevelHardcore;
    }

    public void setParagonLevelHardcore(int paragonLevelHardcore) {
        this.paragonLevelHardcore = paragonLevelHardcore;
    }

    public HeroKill getKills() {
        return kills;
    }

    public void setKills(HeroKill kills) {
        this.kills = kills;
    }

    public TimePlayed getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(TimePlayed timePlayed) {
        this.timePlayed = timePlayed;
    }

    public int getHighestHardcoreLevel() {
        return highestHardcoreLevel;
    }

    public void setHighestHardcoreLevel(int highestHardcoreLevel) {
        this.highestHardcoreLevel = highestHardcoreLevel;
    }
}
