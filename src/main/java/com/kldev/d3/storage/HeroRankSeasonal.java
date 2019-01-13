package com.kldev.d3.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "hero_rank_seasonal",indexes = {
        @Index(name = "hero_rank__btag_season", columnList = "btag,season"),
        @Index(name = "hero_rank__rank_type", columnList = "rank_type")
})
public class HeroRankSeasonal extends BaseHeroRank {

    @Column(name="season")
    private int season;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
