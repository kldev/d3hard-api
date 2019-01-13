package com.kldev.d3.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "season_rank")
public class RankSeason {


    public RankSeason() {}

    public RankSeason(int season) {
        this.id = String.valueOf(season);
        this.season = season;
    }

    @Id
    @Column
    private String id;

    @Column
    private int season;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
