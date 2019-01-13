package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FallenHero {

    @JsonProperty("heroId")
    private long heroId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("class")
    private String className;

    @JsonProperty("level")
    private int level;

    @JsonProperty("elites")
    private long elites;

    @JsonProperty("hardcore")
    private boolean hardcore;

    @JsonProperty("death")
    private HeroDeath death;

    @JsonProperty("gender")
    private int gender;

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getElites() {
        return elites;
    }

    public void setElites(long elites) {
        this.elites = elites;
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public void setHardcore(boolean hardcore) {
        this.hardcore = hardcore;
    }

    public HeroDeath getDeath() {
        return death;
    }

    public void setDeath(HeroDeath death) {
        this.death = death;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
