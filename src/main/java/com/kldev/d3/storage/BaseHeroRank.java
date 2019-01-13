package com.kldev.d3.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseHeroRank implements IBaseHeroRank {

    @Id
    @Column(name="id", unique = true, length = 255, nullable = false)
    private String id;

    ///The forum login
    @Column(name="forum", unique = false, length = 512, nullable = false)
    private String forum = "";

    @Column(name="btag", unique = false, length = 512, nullable = false)
    private String btag = "";

    @Column(name="guild", length = 512, nullable = false)
    private String guild = "";

    @Column(name="update_date", nullable = false)
    private long updateDate = 0;

    @Column(name="rank_type", nullable = false)
    private String rankType;

    @Column(name="position", nullable = false)
    private int position = 0;

    @Column(name="position_change", nullable = false)
    private int positionChange = 0; // default value 0

    @Column(name="rank_value", nullable = false)
    private int rankValue = 0;

    @Column(name="hero_id", nullable = false)
    private long heroId = 0;

    @Column(name="rank_eu", nullable = false)
    private int rankEu = 0;

    @Column(name="completed_time", nullable = false)
    private long completedTime = 0;

    @Column(name="rank_time", nullable = false)
    private long riftTime = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForum() {
        return forum;
    }

    public void setForum(String forum) {

        if (forum == null) forum = "";
        this.forum = forum;
    }

    public String getBtag() {
        return btag;
    }

    public void setBtag(String btag) {
        this.btag = btag;
    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        if (guild == null) guild = "";
        this.guild = guild;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRankValue() {
        return rankValue;
    }

    public void setRankValue(int rankValue) {
        this.rankValue = rankValue;
    }

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    public int getRankEu() {
        return rankEu;
    }

    public void setRankEu(int rankEu) {
        this.rankEu = rankEu;
    }

    public long getRiftTime() {
        return riftTime;
    }

    public void setRiftTime(long riftTime) {
        this.riftTime = riftTime;
    }

    public long getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(long completedTime) {
        this.completedTime = completedTime;
    }

    public int getPositionChange() {
        return positionChange;
    }

    public void setPositionChange(int positionChange) {
        this.positionChange = positionChange;
    }
}
