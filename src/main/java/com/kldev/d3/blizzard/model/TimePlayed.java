package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimePlayed {

    @JsonProperty("demon-hunter")
    private double demonHunter;

    @JsonProperty("barbarian")
    private double barbarian;

    @JsonProperty("witch-doctor")
    private double witchDoctor;

    @JsonProperty("wizard")
    private double wizard;

    @JsonProperty("monk")
    private double monk;

    @JsonProperty("crusader")
    private double crusader;


    public double getDemonHunter() {
        return demonHunter;
    }

    public void setDemonHunter(double demonHunter) {
        this.demonHunter = demonHunter;
    }

    public double getBarbarian() {
        return barbarian;
    }

    public void setBarbarian(double barbarian) {
        this.barbarian = barbarian;
    }

    public double getWitchDoctor() {
        return witchDoctor;
    }

    public void setWitchDoctor(double witchDoctor) {
        this.witchDoctor = witchDoctor;
    }

    public double getWizard() {
        return wizard;
    }

    public void setWizard(double wizard) {
        this.wizard = wizard;
    }

    public double getMonk() {
        return monk;
    }

    public void setMonk(double monk) {
        this.monk = monk;
    }

    public double getCrusader() {
        return crusader;
    }

    public void setCrusader(double crusader) {
        this.crusader = crusader;
    }
}
