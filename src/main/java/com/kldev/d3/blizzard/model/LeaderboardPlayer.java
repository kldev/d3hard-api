package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardPlayer {

    public final static String HeroBattleTag = "HeroBattleTag";
    public final static String HeroClass = "HeroClass";
    public final static String ParagonLevel = "ParagonLevel";
    public final static String ClanName = "ClanName";
    public final static String HeroId = "HeroId";

    private String key;
    private long accountId;

    @JsonProperty("data")
    private List<LeaderboardPlayerData> data = new ArrayList<>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public List<LeaderboardPlayerData> getData() {
        return data;
    }

    public void setData(List<LeaderboardPlayerData> data) {
        this.data = data;
    }


    public String HeroBattleTag()
    {
        for (LeaderboardPlayerData item : this.data)
        {
            if (item.getId().equalsIgnoreCase("HeroBattleTag"))
            {
                return item.getStringValue();
            }
        }

        return "";
    }

    public String HeroClass()
    {
        for (LeaderboardPlayerData item : this.data)
        {
            if (item.getId().equalsIgnoreCase("HeroClass"))
            {
                return item.getStringValue();
            }
        }

        return "";
    }

    public String ClanName()
    {
        for (LeaderboardPlayerData item : this.data)
        {
            if (item.getId().equalsIgnoreCase("ClanName"))
            {
                return item.getStringValue();
            }
        }

        return "";
    }

    public long ParagonLevel()
    {
        for (LeaderboardPlayerData item : this.data)
        {
            if (item.getId().equalsIgnoreCase("ParagonLevel"))
            {
                return item.getNumberValue();
            }
        }

        return 0;
    }

    public long HeroId()
    {
        for (LeaderboardPlayerData item : this.data)
        {
            if (item.getId().equalsIgnoreCase("HeroId"))
            {
                return item.getNumberValue();
            }
        }

        return 0;
    }
}
