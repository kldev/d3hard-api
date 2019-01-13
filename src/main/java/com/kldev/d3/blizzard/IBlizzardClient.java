package com.kldev.d3.blizzard;

import com.kldev.d3.blizzard.model.*;

import java.util.List;

public interface IBlizzardClient {

    ActIndexResponse getActs();
    Act getAct(int index);
    PlayerAccount getPlayer(String battleTag);
    HeroDetail getHeroDetail(String battleTag, long id);
    int getCurrentSeason();
    List<LeaderboardItem> getLeaderBoard(int season, String leaderBoard);
}
