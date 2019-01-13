package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PlayerAccount {

    @JsonProperty("battleTag")
    private String battleTag;

    @JsonProperty("paragonLevel")
    private long paragonLevel;

    @JsonProperty("paragonLevelHardcore")
    private long paragonLevelHardcore;

    @JsonProperty("paragonLevelSeason")
    private long paragonLevelSeason;

    @JsonProperty("paragonLevelSeasonHardcore")
    private long paragonLevelSeasonHardcore;

    @JsonProperty("guildName")
    private String guildName;

    @JsonProperty("heroes")
    private HeroList heroes;

    @JsonProperty("lastHeroPlayed")
    private long lastHeroPlayed;

    @JsonProperty("lastUpdated")
    private long lastUpdated;

    @JsonProperty("kills")
    private HeroKill kills;

    @JsonProperty("highestHardcoreLevel")
    private int highestHardcoreLevel;

    @JsonProperty("timePlayed")
    private TimePlayed timePlayed;

    @JsonProperty("progression")
    private Progression progression;

    @JsonProperty("fallenHeroes")
    private FallenHeroList fallenHeroes;

    @JsonProperty("blacksmithSeason")
    private SeasonInfo blacksmithSeason;

    @JsonProperty("jewelerSeason")
    private SeasonInfo jewelerSeason;

    @JsonProperty("mysticSeason")
    private SeasonInfo mysticSeason;

    @JsonProperty("blacksmithHardcore")
    private SeasonInfo blacksmithHardcore;

    @JsonProperty("jewelerHardcore")
    private SeasonInfo jewelerHardcore;

    @JsonProperty("mysticHardcore")
    private SeasonInfo mysticHardcore;

    @JsonProperty("blacksmithSeasonHardcore")
    private SeasonInfo blacksmithSeasonHardcore;

    @JsonProperty("jewelerSeasonHardcore")
    private SeasonInfo jewelerSeasonHardcore;

    @JsonProperty("mysticSeasonHardcore")
    private SeasonInfo mysticSeasonHardcore;

    @JsonProperty("blacksmith")
    private SeasonInfo blacksmith;

    @JsonProperty("jeweler")
    private SeasonInfo jeweler;

    @JsonProperty("mystic")
    private SeasonInfo mystic;

    @JsonProperty("seasonalProfiles")
    private SeasonalProfiles seasonalProfiles;

    public String getBattleTag() {
        return battleTag;
    }

    public void setBattleTag(String battleTag) {
        this.battleTag = battleTag;
    }

    public long getParagonLevel() {
        return paragonLevel;
    }

    public void setParagonLevel(long paragonLevel) {
        this.paragonLevel = paragonLevel;
    }

    public long getParagonLevelHardcore() {
        return paragonLevelHardcore;
    }

    public void setParagonLevelHardcore(long paragonLevelHardcore) {
        this.paragonLevelHardcore = paragonLevelHardcore;
    }

    public long getParagonLevelSeason() {
        return paragonLevelSeason;
    }

    public void setParagonLevelSeason(long paragonLevelSeason) {
        this.paragonLevelSeason = paragonLevelSeason;
    }

    public long getParagonLevelSeasonHardcore() {
        return paragonLevelSeasonHardcore;
    }

    public void setParagonLevelSeasonHardcore(long paragonLevelSeasonHardcore) {
        this.paragonLevelSeasonHardcore = paragonLevelSeasonHardcore;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public HeroList getHeroes() {
        return heroes;
    }

    public void setHeroes(HeroList heroes) {
        this.heroes = heroes;
    }

    public long getLastHeroPlayed() {
        return lastHeroPlayed;
    }

    public void setLastHeroPlayed(long lastHeroPlayed) {
        this.lastHeroPlayed = lastHeroPlayed;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public HeroKill getKills() {
        return kills;
    }

    public void setKills(HeroKill kills) {
        this.kills = kills;
    }

    public int getHighestHardcoreLevel() {
        return highestHardcoreLevel;
    }

    public void setHighestHardcoreLevel(int highestHardcoreLevel) {
        this.highestHardcoreLevel = highestHardcoreLevel;
    }

    public TimePlayed getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(TimePlayed timePlayed) {
        this.timePlayed = timePlayed;
    }

    public Progression getProgression() {
        return progression;
    }

    public void setProgression(Progression progression) {
        this.progression = progression;
    }

    public FallenHeroList getFallenHeroes() {
        return fallenHeroes;
    }

    public void setFallenHeroes(FallenHeroList fallenHeroes) {
        this.fallenHeroes = fallenHeroes;
    }

    public SeasonInfo getBlacksmithSeason() {
        return blacksmithSeason;
    }

    public void setBlacksmithSeason(SeasonInfo blacksmithSeason) {
        this.blacksmithSeason = blacksmithSeason;
    }

    public SeasonInfo getJewelerSeason() {
        return jewelerSeason;
    }

    public void setJewelerSeason(SeasonInfo jewelerSeason) {
        this.jewelerSeason = jewelerSeason;
    }

    public SeasonInfo getMysticSeason() {
        return mysticSeason;
    }

    public void setMysticSeason(SeasonInfo mysticSeason) {
        this.mysticSeason = mysticSeason;
    }

    public SeasonInfo getBlacksmithHardcore() {
        return blacksmithHardcore;
    }

    public void setBlacksmithHardcore(SeasonInfo blacksmithHardcore) {
        this.blacksmithHardcore = blacksmithHardcore;
    }

    public SeasonInfo getJewelerHardcore() {
        return jewelerHardcore;
    }

    public void setJewelerHardcore(SeasonInfo jewelerHardcore) {
        this.jewelerHardcore = jewelerHardcore;
    }

    public SeasonInfo getMysticHardcore() {
        return mysticHardcore;
    }

    public void setMysticHardcore(SeasonInfo mysticHardcore) {
        this.mysticHardcore = mysticHardcore;
    }

    public SeasonInfo getBlacksmithSeasonHardcore() {
        return blacksmithSeasonHardcore;
    }

    public void setBlacksmithSeasonHardcore(SeasonInfo blacksmithSeasonHardcore) {
        this.blacksmithSeasonHardcore = blacksmithSeasonHardcore;
    }

    public SeasonInfo getJewelerSeasonHardcore() {
        return jewelerSeasonHardcore;
    }

    public void setJewelerSeasonHardcore(SeasonInfo jewelerSeasonHardcore) {
        this.jewelerSeasonHardcore = jewelerSeasonHardcore;
    }

    public SeasonInfo getMysticSeasonHardcore() {
        return mysticSeasonHardcore;
    }

    public void setMysticSeasonHardcore(SeasonInfo mysticSeasonHardcore) {
        this.mysticSeasonHardcore = mysticSeasonHardcore;
    }

    public SeasonInfo getBlacksmith() {
        return blacksmith;
    }

    public void setBlacksmith(SeasonInfo blacksmith) {
        this.blacksmith = blacksmith;
    }

    public SeasonInfo getJeweler() {
        return jeweler;
    }

    public void setJeweler(SeasonInfo jeweler) {
        this.jeweler = jeweler;
    }

    public SeasonInfo getMystic() {
        return mystic;
    }

    public void setMystic(SeasonInfo mystic) {
        this.mystic = mystic;
    }

    public SeasonalProfiles getSeasonalProfiles() {
        return seasonalProfiles;
    }

    public void setSeasonalProfiles(SeasonalProfiles seasonalProfiles) {
        this.seasonalProfiles = seasonalProfiles;
    }
}
