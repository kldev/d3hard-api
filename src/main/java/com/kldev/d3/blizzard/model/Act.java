package com.kldev.d3.blizzard.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Act {


    @JsonProperty("slug")
    private String slug;

    @JsonProperty("number")
    private long number;

    @JsonProperty("name")
    private String name;

    @JsonProperty("quests")
    private ArrayList<ActQuest> quests;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ActQuest> getQuests() {
        return quests;
    }

    public void setQuests(ArrayList<ActQuest> quests) {
        this.quests = quests;
    }
}
