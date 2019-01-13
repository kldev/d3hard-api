package com.kldev.d3.command;

import com.kldev.d3.blizzard.constant.LeaderboardTypes;
import com.kldev.d3.controller.model.BaseRequest;
import com.kldev.d3.controller.model.CreateHeroRankRequest;
import com.kldev.d3.controller.model.CreateRankRequest;
import com.kldev.d3.util.JsonUtil;
import io.dropwizard.cli.Command;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import okhttp3.*;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class UpdateAllCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(UpdateAllCommand.class);

    public UpdateAllCommand() {

        super("update", "update all account form player ranks");


        // example usage
        // java -jar app.jar update -j "jdbc:mysql://mysql-service:3306/d3hard?useSSL=false&user=d3hard&password=d3hard" -u "http://localhost:9000"
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void configure(Subparser subparser) {

        subparser.addArgument("-j", "--jdbc")
                .dest("jdbc")
                .type(String.class)
                .required(true)
                .help("The jdbc connection");

        subparser.addArgument("-u", "--url")
                .dest("url")
                .type(String.class)
                .required(true)
                .help("The running server url");
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void run(Bootstrap<?> bootstrap, Namespace namespace) throws Exception {

        String jdbc = namespace.getString("jdbc");
        String url = namespace.getString("url");

        Statement stmt = null;
        ResultSet rs = null;
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();




        Connection conn =
                DriverManager.getConnection(jdbc);

        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT btag, forum FROM player_rank");

        List<CreateRankRequest> list = new ArrayList<>();

        while (rs.next())
        {
            String btag = rs.getString("btag");
            String forum = rs.getString("forum");
            CreateRankRequest request = new CreateRankRequest();
            request.setbTag(btag);
            request.setForumId(forum);

            list.add(request);
        }

        rs.close();
        stmt.close();
        conn.close();


        logAndPrint("Loaded ranks:" + list.size());
       // updatePlayerRanks(jdbc, list, url);



        logAndPrint("Update heroes postion");

        List<String> leaderboardTypes = Arrays.asList(
                LeaderboardTypes.RiffHardcoreBarbarian,
                LeaderboardTypes.RiffBarbarian,
                LeaderboardTypes.RiffHardcoreCrusader,
                LeaderboardTypes.RiffCrusader,
                LeaderboardTypes.RiffHardcoreDeamonHunter,
                LeaderboardTypes.RiffDeamonHunter,
                LeaderboardTypes.RiffHardcoreMonk,
                LeaderboardTypes.RiffMonk,
                LeaderboardTypes.RiffHardcoreWitchDoctor,
                LeaderboardTypes.RiffWitchDoctor,
                LeaderboardTypes.RiffHardcoreWizard,
                LeaderboardTypes.RiffWizard,
                LeaderboardTypes.RiffHardcoreNecromancer,
                LeaderboardTypes.RiffNecromancer,
                LeaderboardTypes.RiffHardcoreTeamTwo,
                LeaderboardTypes.RiffTeamTwo,
                LeaderboardTypes.RiffHardcoreTeamThree,
                LeaderboardTypes.RiffTeamThree,
                LeaderboardTypes.RiffHardcoreTeamFour,
                LeaderboardTypes.RiffTeamFour
        );

        int maxSeason = getMaxSeason(jdbc);


        for (String type: leaderboardTypes) {

            for (int i =0; i<= maxSeason; i++) {
                try {

                    updateHeros(type, i, url);
                }
                catch (Exception ex){
                    logAndPrint(ex.getMessage());
                }
            }
        }

    }

    @SuppressWarnings("Duplicates")
    private void updatePlayerRanks(String jdbc,  List<CreateRankRequest> list, String url ) throws Exception
    {
        if (list.size() > 0)
        {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .callTimeout(Duration.ofHours(1))
                    .readTimeout(Duration.ofHours(1)).build();


            int i = 1;
            for (CreateRankRequest rankRequest: list)
            {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();


                int j= 0;
                int responseCode = 0;
                while (j < 10){
                    responseCode = sendUpdate(rankRequest, url);
                    if ( responseCode != -1) break;

                    Thread.sleep(5*1000);
                    j++;
                }

                stopWatch.stop();
                logAndPrint("Update [" + i++ +"]: " + rankRequest.getbTag() + " has ended with code - " + responseCode + " in " + stopWatch.getTime(TimeUnit.SECONDS) +  " .s ");

            }

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            Request request = new Request.Builder().url(url + "/api/rank/updatePosition")
                    .header("Content-Type", "application/json").post(RequestBody.create(MediaType
                            .parse("application/json"), JsonUtil.toJsonString(new BaseRequest()))).build();

            Response response = okHttpClient.newCall(request).execute();

            stopWatch.stop();
            logAndPrint("Update position has ended with code - " + response.code() + " in " + stopWatch.getTime(TimeUnit.SECONDS) +  " .s ");


        }
    }

    @SuppressWarnings("Duplicates")
    private int getMaxSeason(String jdbc) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        Connection conn =
                DriverManager.getConnection(jdbc);

        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT MAX(season) as maxSeason FROM season_rank");

        int season = 0;

        if (rs.next())
        {
            season = rs.getInt("maxSeason");
        }

        rs.close();
        stmt.close();
        conn.close();

        return season;
    }

    private void updateHeros(String rankType, int season, String url)
    {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CreateHeroRankRequest request = new CreateHeroRankRequest();
        request.setRankType(rankType);
        request.setSeason(season);

        logAndPrint("Update heroes " + rankType + ", season: " + season);

        int responseCode = sendHerosUpdate(request, url);

        stopWatch.stop();
        logAndPrint("Update has ended with code - " + responseCode + " in " + stopWatch.getTime(TimeUnit.SECONDS) +  " .s ");


    }

    @SuppressWarnings("Duplicates")
    private int sendHerosUpdate(CreateHeroRankRequest rank, String url)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(Duration.ofHours(1))
                .readTimeout(Duration.ofHours(1)).build();

        Request request = new Request.Builder().url(url + "/api/rank/updateHeros")
                .header("Content-Type", "application/json").post(RequestBody.create(MediaType
                        .parse("application/json"), JsonUtil.toJsonString(rank))).build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.code();

        }
        catch (java.net.SocketTimeoutException ts)
        {
            return -1;
        }
        catch (java.io.IOException io){
            return -1;
        }


    }


    @SuppressWarnings("Duplicates")
    private int sendUpdate(CreateRankRequest rank, String url)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(Duration.ofHours(1))
                .readTimeout(Duration.ofHours(1)).build();

        Request request = new Request.Builder().url(url + "/api/rank/create")
                .header("Content-Type", "application/json").post(RequestBody.create(MediaType
                        .parse("application/json"), JsonUtil.toJsonString(rank))).build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.code();

        }
        catch (java.net.SocketTimeoutException ts)
        {
            return -1;
        }
        catch (java.io.IOException io){
            return -1;
        }


    }

    private void logAndPrint(String message)
    {
        logger.info(message);
        System.out.println(message);
    }
}
