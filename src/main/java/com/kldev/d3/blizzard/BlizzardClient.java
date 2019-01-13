package com.kldev.d3.blizzard;

import com.kldev.d3.blizzard.model.*;
import com.kldev.d3.util.JsonUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class BlizzardClient implements IBlizzardClient {

    private final String GET_TOKEN = "https://eu.battle.net/oauth/token";
    private final String BASE_URL = "https://eu.api.blizzard.com";
    private final String REGION = "eu";

    private final String clientId;
    private final String secret;
    private String accessToken;


    public BlizzardClient(String clientId, String secret) {
        this.clientId = clientId;
        this.secret = secret;
    }

    // https://develop.battle.net/documentation/guides/getting-started
    public boolean logIn() {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(null,"grant_type=client_credentials" );
        Request request = new Request.Builder().url(GET_TOKEN)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", getTokenAuthorization())
                .post(body)
                .build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null && response.code() == 200)
        {
            try {
                this.accessToken = JsonUtil.parse( response.body().string(), OAuthTokenResponse.class).getAccessToken() ;
            } catch (IOException e) {
                e.printStackTrace();
            }

            response.close();
            return true;
        }

        return false;

    }

    private String getTokenAuthorization() {

        String auth = String.format("%s:%s", this.clientId, this.secret);

        String basic = String.format("Basic %s", toBase64(auth));

        return basic;
    }

    private String toBase64(String value) {
        try {
            return java.util.Base64.getEncoder().encodeToString(value.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "wrong access";
    }

    private <R> ClientResponse<R> callApi(String pathAndQuery, Class<R> clazz) {
        logIn(); // TODO: rewrite to check token valid / login when necesery

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + pathAndQuery)
                .addHeader("Authorization", "bearer " + accessToken)
                .build();

        writeLog("bearer " + accessToken);
        writeLog(BASE_URL + pathAndQuery);

        ClientResponse<R> result = null;
        try {
            result = ClientResponse.class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Response response;
        try {
            response = client.newCall(request).execute();

            writeLog("response code: " + response.code() );
            if (response.code() == 200) {

                String responseBody = response.body().string();

                writeLog(responseBody );

                result.setStatus(200);
                result.setResult(JsonUtil.mapper.readValue(responseBody, clazz));
            } else {
                result.setStatus(response.code());
            }

            response.close();
            Thread.sleep(50);

        } catch (IOException e) {
            result.setStatus(503);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return result;

    }

    private void writeLog(String s) {
        System.out.println(s);
    }

    public UriBuilder getBuilder()
    {
        return UriBuilder.fromPath("/");
    }

    public ActIndexResponse getActs()
    {

        UriBuilder builder = getBuilder();

        URI uri = builder.path("/d3/data/act")
                .queryParam("region", REGION).build();

        ClientResponse<ActIndexResponse> response = callApi(uri.toString(), ActIndexResponse.class);

        return response.getResult();
    }

    @Override
    public Act getAct(int index)
    {
        UriBuilder builder = getBuilder();

        URI uri = builder.path("/d3/data/act/{index}").build(index);


        ClientResponse<Act> response = callApi(uri.toString(), Act.class);

        return response.getResult();
    }


    @Override
    public PlayerAccount getPlayer(String battleTag)
    {
        UriBuilder builder = getBuilder();

        URI uri = builder.path("/d3/profile/{tag}/")
                .queryParam("region", REGION)
                .build(battleTag);


        ClientResponse<PlayerAccount> response = callApi(uri.toString(), PlayerAccount.class);

        if (response.getStatus() != 200) return null;

        return response.getResult();
    }

    @Override
    public HeroDetail getHeroDetail(String battleTag, long id) {

        UriBuilder builder = getBuilder();

        URI uri = builder.path("/d3/profile/{tag}/hero/{id}")
                .build(battleTag, id);

        ClientResponse<HeroDetail> response = callApi(uri.toString(), HeroDetail.class);

        if (response.getStatus() != 200) return null;

        return response.getResult();

    }

    @Override
    public int getCurrentSeason() {
        UriBuilder builder = getBuilder();

        URI uri = builder.path("/data/d3/season/").build();

        ClientResponse<SeasonListResponse> response = callApi(uri.toString(), SeasonListResponse.class);

        if (response.getStatus() != 200) return 0;

        return response.getResult().getCurrentSeason();
    }

    @Override
    public List<LeaderboardItem> getLeaderBoard(int season, String leaderBoard){

        UriBuilder builder = getBuilder();

        URI uri = builder.path("/data/d3/season/{season}/leaderboard/{leaderBoard}").build(season, leaderBoard);

        ClientResponse<LeaderboardResponse> response = callApi(uri.toString(), LeaderboardResponse.class);

        if (response.getStatus() != 200) return new ArrayList<>();

        return response.getResult().getRowList();

    }
}
