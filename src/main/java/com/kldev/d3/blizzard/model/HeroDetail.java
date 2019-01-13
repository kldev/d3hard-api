package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HeroDetail {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("class")
    private String className;   // barbarian, crusader, wizard, witch-doctor, demon-hunter, monk

    @JsonProperty("gender")
    private int gender;

    @JsonProperty("level")
    private int level;

    @JsonProperty("paragonLevel")
    private int paragonLevel;

    @JsonProperty("hardcore")
    private boolean hardcore;

    @JsonProperty("seasonal")
    private boolean seasonal;

    @JsonProperty("seasonCreated")
    private int seasonCreated;

    @JsonProperty("highestSoloRiftCompleted")
    private int highestSoloRiftCompleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParagonLevel() {
        return paragonLevel;
    }

    public void setParagonLevel(int paragonLevel) {
        this.paragonLevel = paragonLevel;
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public void setHardcore(boolean hardcore) {
        this.hardcore = hardcore;
    }

    public boolean isSeasonal() {
        return seasonal;
    }

    public void setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
    }

    public int getSeasonCreated() {
        return seasonCreated;
    }

    public void setSeasonCreated(int seasonCreated) {
        this.seasonCreated = seasonCreated;
    }

    public int getHighestSoloRiftCompleted() {
        return highestSoloRiftCompleted;
    }

    public void setHighestSoloRiftCompleted(int highestSoloRiftCompleted) {
        this.highestSoloRiftCompleted = highestSoloRiftCompleted;
    }
}
