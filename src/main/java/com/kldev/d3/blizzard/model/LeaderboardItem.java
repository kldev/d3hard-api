package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardItem {


    public final static String Rank = "Rank";
    public final static String AchievementPoints = "AchievementPoints";
    public final static String CompletedTime = "CompletedTime";
    public final static String RiftLevel = "RiftLevel";
    public final static String RiftTime = "RiftTime";



    @JsonProperty("player")
    private List<LeaderboardPlayer> player = new ArrayList<>();

    private long order = 0;

    @JsonProperty("data")
    private List<LeaderboardPlayerData> data = new ArrayList<>();



    public long AchievementPoints()
    {
        for (LeaderboardPlayerData item : this.data)
        {
            if (item.getId().equalsIgnoreCase("AchievementPoints"))
            {
                return item.getNumberValue();
            }
        }

        return 0;
    }

    public long Rank()
    {
        for (LeaderboardPlayerData item : this.data)
        {
            if (item.getId().equalsIgnoreCase("Rank"))
            {
                return item.getNumberValue();
            }
        }

        return 0;
    }

    public long CompletedTime()
    {
        for (LeaderboardPlayerData item : this.data)
        {
            if (item.getId().equalsIgnoreCase("CompletedTime"))
            {
                return item.getTimestampValue();
            }
        }

        return 0;
    }

    public long RiftTime()
    {
        for (LeaderboardPlayerData item : this.data)
        {
            if (item.getId().equalsIgnoreCase("RiftTime"))
            {
                return item.getTimestampValue();
            }
        }

        return 0;
    }


    public long HeroId()
    {
        return this.getPlayer().HeroId();
    }

    public long RiftLevel() {
        for (LeaderboardPlayerData item : this.data)
        {
            if (item.getId().equalsIgnoreCase("RiftLevel"))
            {
                return item.getNumberValue();
            }
        }

        return 0;
    }


    public LeaderboardPlayer getPlayer() {
        return player.size() > 0 ? player.get(0) : new LeaderboardPlayer();
    }

    public LeaderboardPlayerData getData() {
        return data.size() > 0 ? data.get(0) : new LeaderboardPlayerData();
    }

    public long getOrder() {
        return order;
    }
}
