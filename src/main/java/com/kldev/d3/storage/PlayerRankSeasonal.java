package com.kldev.d3.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "player_rank_seasonal",indexes = {
        @Index(name = "player_rank__btag_season", columnList = "btag,season")
})
public class PlayerRankSeasonal extends BasePlayerRank {

    @Column(name="season")
    private int season;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
