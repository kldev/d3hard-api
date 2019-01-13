package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HeroKill {

    @JsonProperty("elites")
    private long elites;

    @JsonProperty("monsters")
    private long monsters;

    @JsonProperty("hardcoreMonsters")
    private long hardcoreMonsters;

    public long getElites() {
        return elites;
    }

    public void setElites(long elites) {
        this.elites = elites;
    }

    public long getMonsters() {
        return monsters;
    }

    public void setMonsters(long monsters) {
        this.monsters = monsters;
    }

    public long getHardcoreMonsters() {
        return hardcoreMonsters;
    }

    public void setHardcoreMonsters(long hardcoreMonsters) {
        this.hardcoreMonsters = hardcoreMonsters;
    }
}
