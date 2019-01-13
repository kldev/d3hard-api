package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HeroDeath {

    @JsonProperty("killer")
    private long killer;

    @JsonProperty("time")
    private long time;

    public long getKiller() {
        return killer;
    }

    public void setKiller(long killer) {
        this.killer = killer;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
