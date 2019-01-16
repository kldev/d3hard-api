package com.kldev.d3.command;

import com.kldev.d3.controller.model.BaseRequest;
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
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ImportFromForumCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(ImportFromForumCommand.class);


    public ImportFromForumCommand() {

        super("forum", "import account from forum");

        // example usage
        // java -jar app.jar forum -j "jdbc:mysql://mysql-service:3306/forum?useSSL=false&user=d3hard&password=d3hard" -u "http://localhost:9000"
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
        rs = stmt.executeQuery("SELECT btag FROM api_konta");

        HashSet<String> btags = new HashSet<>();

        while (rs.next())
        {
            String btag = rs.getString("btag");
            if (btags.contains(btag)) continue;

            btags.add(btag);
        }

        rs.close();
        stmt.close();
        conn.close();


        logAndPrint("Loaded btags:" + btags.size());

        if (btags.size() > 0)
        {


            AtomicInteger i = new AtomicInteger(1);


            btags.stream().parallel().forEach(k -> {
                try {
                    importAccount(url, k, i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .callTimeout(Duration.ofHours(1))
                    .readTimeout(Duration.ofHours(1)).build();

            Request request = new Request.Builder().url(url + "/api/rank/updatePosition")
                    .header("Content-Type", "application/json").post(RequestBody.create(MediaType
                            .parse("application/json"), JsonUtil.toJsonString(new BaseRequest()))).build();

            Response response = okHttpClient.newCall(request).execute();

            stopWatch.stop();
            logAndPrint("Update position has ended with code - " + response.code() + " in " + stopWatch.getTime(TimeUnit.SECONDS) +  " .s ");

        }


    }

    @SuppressWarnings("Duplicates")
    private void importAccount(String url, String btag, AtomicInteger i) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        int j= 0;
        int responseCode = 0;
        while (j < 10){
            responseCode = sendUpdate(btag, url);
            if ( responseCode != -1) break;


            Thread.sleep(5*1000);


            j++;
        }

        stopWatch.stop();

        logAndPrint("Import [" + i.getAndIncrement() +"]: "  + btag + " has ended with code - " + responseCode + " in " + stopWatch.getTime(TimeUnit.SECONDS) +  " .s ");
    }


    @SuppressWarnings("Duplicates")
    private int sendUpdate(String btag, String url)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(Duration.ofHours(1))
                .readTimeout(Duration.ofHours(1)).build();

        CreateRankRequest rank = new CreateRankRequest();
        //rank.setForumId(btag);
        rank.setbTag(btag);

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


    // create task process rank
}
