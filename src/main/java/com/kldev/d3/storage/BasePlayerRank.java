package com.kldev.d3.storage;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BasePlayerRank {

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

    @Column(name="paragon_level", nullable = false)
    private int paragonLevel = 0;

    @Column(name="paragon_level_hc", nullable = false)
    private int paragonLevelHc = 0;

    @Column(name="update_date", nullable = false)
    private long updateDate = 0;

    @Column(name="position", nullable = false)
    private int position = 0;

    @Column(name="position_change", nullable = false)
    private int positionChange = 0;

    @Column(name="position_hc", nullable = false)
    private int positionHc = 0;

    @Column(name="position_hc_change", nullable = false)
    private int positionHcChange = 0;

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
        this.forum = forum;
    }

    public String getBtag() {
        return btag;
    }

    public void setBtag(String btag) {
        this.btag = btag;
    }

    public int getParagonLevel() {
        return paragonLevel;
    }

    public void setParagonLevel(int paragonLevel) {
        this.paragonLevel = paragonLevel;
    }

    public int getParagonLevelHc() {
        return paragonLevelHc;
    }

    public void setParagonLevelHc(int paragonLevelHc) {
        this.paragonLevelHc = paragonLevelHc;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPositionChange() {
        return positionChange;
    }

    public void setPositionChange(int positionChange) {
        this.positionChange = positionChange;
    }

    public int getPositionHc() {
        return positionHc;
    }

    public void setPositionHc(int positionHc) {
        this.positionHc = positionHc;
    }

    public int getPositionHcChange() {
        return positionHcChange;
    }

    public void setPositionHcChange(int positionHcChange) {
        this.positionHcChange = positionHcChange;
    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }
}
