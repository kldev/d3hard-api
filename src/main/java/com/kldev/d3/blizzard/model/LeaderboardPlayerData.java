package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LeaderboardPlayerData {



    @JsonProperty("id")
    private String id;

    @JsonProperty("string")
    private String stringValue;

    @JsonProperty("number")
    private long numberValue;

    @JsonProperty("timestamp")
    private long timestampValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public long getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(long numberValue) {
        this.numberValue = numberValue;
    }

    public long getTimestampValue() {
        return timestampValue;
    }

    public void setTimestampValue(long timestampValue) {
        this.timestampValue = timestampValue;
    }
}
