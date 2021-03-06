package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hero {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("class")
    private String className;   // barbarian, crusader, wizard, witch-doctor, demon-hunter, monk

    @JsonProperty("classSlug")
    private String classSlug;

    @JsonProperty("gender")
    private int gender;

    @JsonProperty("level")
    private int level;

    @JsonProperty("kills")
    private HeroKill kills;

    @JsonProperty("paragonLevel")
    private int paragonLevel;

    @JsonProperty("hardcore")
    private boolean hardcore;

    @JsonProperty("seasonal")
    private boolean seasonal;

    @JsonProperty("dead")
    private boolean dead;

    @JsonProperty("last-updated")
    private long lastUpdated;

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

    public String getClassSlug() {
        return classSlug;
    }

    public void setClassSlug(String classSlug) {
        this.classSlug = classSlug;
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

    public HeroKill getKills() {
        return kills;
    }

    public void setKills(HeroKill kills) {
        this.kills = kills;
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
