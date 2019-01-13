package com.kldev.d3;

import com.kldev.d3.blizzard.BlizzardClient;
import com.kldev.d3.blizzard.constant.LeaderboardTypes;
import com.kldev.d3.blizzard.model.*;
import com.kldev.d3.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

public class BlizardClientTest {

    private final String TEST_BATTLE_TAG = "USER#0000";
    private static final String CLIENT_ID = "YOUR_CLIENT_ID"; 
    private static final String SECRET = "YOUR_CLIENT_SECRET";

    private static BlizzardClient blizzardClient;

    @BeforeAll
    public static void setup() {
        blizzardClient =  new BlizzardClient(CLIENT_ID, SECRET);
    }

    @Test
    public void getActs()
    {
        ActIndexResponse response = blizzardClient.getActs();

        logResponse(response);
        Assertions.assertTrue(response.getActs().size() > 0);
    }

    @Test
    public void getSingleAct()
    {
        Act response = blizzardClient.getAct(1);

        logResponse(response);
        Assertions.assertNotNull(response);
    }

    @Test
    public void getPlayerAccount()
    {
        PlayerAccount response = blizzardClient.getPlayer(TEST_BATTLE_TAG);

        logResponse(response);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getSeasonalProfiles());

        Assertions.assertNotNull(response.getSeasonalProfiles().getSeason0());
        Assertions.assertNotNull(response.getSeasonalProfiles().getSeason14());

        Assertions.assertNull(response.getSeasonalProfiles().getSeason1());
    }

    @Test
    public void buildTagUrl()
    {
        UriBuilder builder = blizzardClient.getBuilder();

        URI uri = builder.path("/d3/profile/{tag}/")
                .queryParam("region", "eu")
                .build(TEST_BATTLE_TAG);

       String properUrl = uri.toString();

       Assertions.assertEquals("/d3/profile/LeMerco%232272/?region=eu", properUrl);
    }

    private void logResponse(Object obj)
    {
        System.out.println(JsonUtil.toJsonString(obj));
    }


    @Test
    public void getLeaderboardRiffBarbarian()
    {
       getLeaderboard(LeaderboardTypes.RiffBarbarian);

    }

    @Test
    public void getLeaderboardRiffHardcoreBarbarian()
    {
        getLeaderboard(LeaderboardTypes.RiffHardcoreBarbarian);

    }


    @Test
    public void getLeaderboardRiffHardcoreTeamTwo()
    {
        getLeaderboard(LeaderboardTypes.RiffHardcoreTeamTwo);

    }

    public void getLeaderboard(String leaderType)
    {
        List<LeaderboardItem> resposne = blizzardClient.getLeaderBoard(15, leaderType);

        for (LeaderboardItem item : resposne) {
            logResponse( leaderType +" : " + item.getOrder() + " - "  + item.getPlayer().HeroBattleTag() + " " + item.RiftLevel());
        }

        Assertions.assertNotNull(resposne);

    }

    @Test
    public void getCurrentSeason()
    {
        int season = blizzardClient.getCurrentSeason();

        Assertions.assertTrue(season > 0);
    }
}
