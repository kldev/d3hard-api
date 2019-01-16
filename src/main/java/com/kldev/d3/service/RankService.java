package com.kldev.d3.service;

import com.kldev.d3.blizzard.BlizzardClient;
import com.kldev.d3.blizzard.constant.LeaderboardTypes;
import com.kldev.d3.blizzard.model.LeaderboardItem;
import com.kldev.d3.blizzard.model.PlayerAccount;
import com.kldev.d3.blizzard.model.Season;
import com.kldev.d3.blizzard.model.SeasonalProfiles;
import com.kldev.d3.configuration.BlizardConfig;
import com.kldev.d3.controller.model.*;
import com.kldev.d3.storage.*;
import com.kldev.d3.storage.dao.HeroRankSeasonalDao;
import com.kldev.d3.storage.dao.PlayerRankDao;
import com.kldev.d3.storage.dao.PlayerRankSeasonalDao;
import com.kldev.d3.storage.dao.RankSeasonDao;
import com.kldev.d3.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

public class RankService {

    private static final Logger logger = LoggerFactory.getLogger(RankService.class);

    private HeroRankSeasonalDao heroRankSeasonalDao;
    private PlayerRankDao playerRankDao;
    private PlayerRankSeasonalDao playerRankSeasonalDao;
    private BlizzardClient client;

    private RankSeasonDao rankSeasonDao;

    public RankService(HeroRankSeasonalDao heroRankSeasonalDao,
                          PlayerRankDao playerRankDao,
                          PlayerRankSeasonalDao playerRankSeasonalDao,
                          BlizardConfig config,
                          RankSeasonDao rankSeasonDao) {

        this.heroRankSeasonalDao = heroRankSeasonalDao;
        this.playerRankDao = playerRankDao;
        this.playerRankSeasonalDao = playerRankSeasonalDao;
        this.rankSeasonDao = rankSeasonDao;

        this.client = new BlizzardClient(config.getClientId(), config.getClientSecret());
    }



    @SuppressWarnings("Duplicates")
    private PlayerAccount getOrThrowNotFound(GetPlayerRequest request)
    {
        PlayerAccount account = client.getPlayer(request.getbTag());

        if (account == null)
        {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return account;
    }

    @SuppressWarnings("Duplicates")
    public List<PlayerRank> getRanking(GetRankingRequest request)
    {
        List<PlayerRank> playerRanks = playerRankDao.getAll(request);

        return playerRanks;
    }

    @SuppressWarnings("Duplicates")
    public List<PlayerRankSeasonal> getSeasonalRanking(GetRankingSeasonalRequest request) {

        List<PlayerRankSeasonal> playerRanks = playerRankSeasonalDao.getAll(request);

       return playerRanks;
    }

    public String createRank(CreateRankRequest request)
    {
        updateSeasonMax();
        PlayerAccount account = getOrThrowNotFound(request);

        PlayerRank rank = createOrUpdateRank(account, request);
        proccessSeasons(account, rank.getForum());

        return "Create or update - completed";
    }

    public String updateHerosRanks(CreateHeroRankRequest request) {


        createHerosRanks(request.getRankType(), request.getSeason());
        updateHerosPostion(request.getRankType(), request.getSeason());

        return "Finish";
    }


    private void createHerosRanks(String rankType, int season) {

        List<LeaderboardItem> items = client.getLeaderBoard(season, rankType );

        if (items != null && items.size() > 0)
        {

            for (LeaderboardItem item : items)
            {
                PlayerRank playerRank = this.playerRankDao.findByBtag(item.getPlayer().HeroBattleTag());
                if (playerRank == null) continue;

                HeroRankSeasonal heroRankSeasonal = this.heroRankSeasonalDao.getByBtagAndSeason(playerRank.getBtag(), season, rankType);

                if (heroRankSeasonal == null) {
                    heroRankSeasonal = new HeroRankSeasonal();
                    heroRankSeasonal.setId(UUID.randomUUID().toString());
                    heroRankSeasonal.setBtag(playerRank.getBtag());
                    heroRankSeasonal.setForum(playerRank.getForum());
                    heroRankSeasonal.setGuild(playerRank.getGuild());

                }


                if (heroRankSeasonal.getRankValue() > item.RiftLevel()) continue; // ODRAZU inny z wyzszym wynikem

                heroRankSeasonal.setSeason(season);
                heroRankSeasonal.setRankType(rankType);
                heroRankSeasonal.setRankEu((int)item.getOrder());
                heroRankSeasonal.setRankValue((int)item.RiftLevel());
                heroRankSeasonal.setHeroId(item.HeroId());
                heroRankSeasonal.setCompletedTime(item.CompletedTime()); // w sekundach
                heroRankSeasonal.setRiftTime(item.RiftTime()); // w sekundach
                heroRankSeasonal.setUpdateDate(playerRank.getUpdateDate());


                this.heroRankSeasonalDao.addOrUpdate(heroRankSeasonal);
            }
        }

    }

    private void updateSeasonMax() {

        int currentSeason = client.getCurrentSeason();

        if (rankSeasonDao.findBySeason(currentSeason) == null)
        {
            try {
                rankSeasonDao.add(new RankSeason(currentSeason));
            }
            catch (Exception ex) {
                logger.debug(ex.getMessage());
            }
        }

    }

    private PlayerRank createOrUpdateRank(PlayerAccount account, CreateRankRequest request)
    {
        PlayerRank rank = playerRankDao.findByBtag(request.getbTag());

        if (rank == null) {

            processNewRank(account, request);
            rank = playerRankDao.findByBtag(account.getBattleTag());
        }
        else{
            updateRank(rank, account);
        }

        return rank;
    }



    private void proccessSeasons(PlayerAccount account, String forumId) {

        SeasonalProfiles seasonalProfiles = account.getSeasonalProfiles();
        if (seasonalProfiles != null)
        {

            Map<Integer, Season> seasonHashMap = new HashMap<Integer, Season>();

            if (seasonalProfiles.getSeason0() != null) seasonHashMap.put(0, seasonalProfiles.getSeason0());  // non-seasonal result
            if (seasonalProfiles.getSeason1() != null) seasonHashMap.put(1, seasonalProfiles.getSeason1());
            if (seasonalProfiles.getSeason2() != null) seasonHashMap.put(2, seasonalProfiles.getSeason2());
            if (seasonalProfiles.getSeason3() != null) seasonHashMap.put(3, seasonalProfiles.getSeason3());
            if (seasonalProfiles.getSeason4() != null) seasonHashMap.put(4, seasonalProfiles.getSeason4());
            if (seasonalProfiles.getSeason5() != null) seasonHashMap.put(5, seasonalProfiles.getSeason5());
            if (seasonalProfiles.getSeason6() != null) seasonHashMap.put(6, seasonalProfiles.getSeason6());
            if (seasonalProfiles.getSeason7() != null) seasonHashMap.put(7, seasonalProfiles.getSeason7());
            if (seasonalProfiles.getSeason8() != null) seasonHashMap.put(8, seasonalProfiles.getSeason8());
            if (seasonalProfiles.getSeason9() != null) seasonHashMap.put(9, seasonalProfiles.getSeason9());
            if (seasonalProfiles.getSeason10() != null) seasonHashMap.put(10, seasonalProfiles.getSeason10());
            if (seasonalProfiles.getSeason11() != null) seasonHashMap.put(11, seasonalProfiles.getSeason11());
            if (seasonalProfiles.getSeason12() != null) seasonHashMap.put(12, seasonalProfiles.getSeason12());
            if (seasonalProfiles.getSeason13() != null) seasonHashMap.put(13, seasonalProfiles.getSeason13());
            if (seasonalProfiles.getSeason14() != null) seasonHashMap.put(14, seasonalProfiles.getSeason14());
            if (seasonalProfiles.getSeason15() != null) seasonHashMap.put(15, seasonalProfiles.getSeason15());
            if (seasonalProfiles.getSeason16() != null) seasonHashMap.put(16, seasonalProfiles.getSeason16());
            if (seasonalProfiles.getSeason17() != null) seasonHashMap.put(17, seasonalProfiles.getSeason17());
            if (seasonalProfiles.getSeason18() != null) seasonHashMap.put(18, seasonalProfiles.getSeason18());
            if (seasonalProfiles.getSeason19() != null) seasonHashMap.put(19, seasonalProfiles.getSeason19());
            if (seasonalProfiles.getSeason20() != null) seasonHashMap.put(20, seasonalProfiles.getSeason20());
            if (seasonalProfiles.getSeason21() != null) seasonHashMap.put(21, seasonalProfiles.getSeason21());
            if (seasonalProfiles.getSeason22() != null) seasonHashMap.put(22, seasonalProfiles.getSeason22());
            if (seasonalProfiles.getSeason23() != null) seasonHashMap.put(23, seasonalProfiles.getSeason23());
            if (seasonalProfiles.getSeason24() != null) seasonHashMap.put(24, seasonalProfiles.getSeason24());
            if (seasonalProfiles.getSeason25() != null) seasonHashMap.put(25, seasonalProfiles.getSeason25());
            if (seasonalProfiles.getSeason26() != null) seasonHashMap.put(26, seasonalProfiles.getSeason26());
            if (seasonalProfiles.getSeason27() != null) seasonHashMap.put(27, seasonalProfiles.getSeason27());
            if (seasonalProfiles.getSeason28() != null) seasonHashMap.put(28, seasonalProfiles.getSeason28());
            if (seasonalProfiles.getSeason29() != null) seasonHashMap.put(29, seasonalProfiles.getSeason29());
            if (seasonalProfiles.getSeason30() != null) seasonHashMap.put(30, seasonalProfiles.getSeason30());

            if (seasonHashMap.size() > 0)
            {
               Iterator<Integer> iterator = seasonHashMap.keySet().iterator();
               while (iterator.hasNext())
               {
                   Integer seasonId = iterator.next();
                   saveSeason(account, seasonHashMap.get(seasonId), seasonId.intValue(), forumId);
               }
            }
        }
    }


    @SuppressWarnings("Duplicates")
    private void saveSeason(PlayerAccount account, Season season, int seasonId, String forumId) {

        playerRankSeasonalDao.deleteAllByBtagAndSeason(account.getBattleTag(), seasonId);

        PlayerRankSeasonal seasonal = new PlayerRankSeasonal();

        seasonal.setBtag(account.getBattleTag());
        seasonal.setGuild(account.getGuildName());
        seasonal.setForum(forumId);

        if (forumId != null && forumId.isEmpty() == false) {
            seasonal.setForum(forumId);
        }

        seasonal.setUpdateDate(account.getLastUpdated()*1000);
        // seasonal.setLoginDate(Date.from(Instant.now()).getTime());

        seasonal.setParagonLevel((int)season.getParagonLevel());
        seasonal.setParagonLevelHc((int)season.getParagonLevelHardcore());

        seasonal.setSeason(seasonId);

        playerRankSeasonalDao.add(seasonal);
    }


    @SuppressWarnings("Duplicates")
    private void updateRank(PlayerRank rank, PlayerAccount account) {

        rank.setUpdateDate(account.getLastUpdated()*1000 );
        // rank.setLoginDate(Date.from(Instant.now()).getTime());
        rank.setGuild(account.getGuildName());


        rank.setParagonLevel((int)account.getParagonLevel());
        rank.setParagonLevelHc((int)account.getParagonLevelHardcore());

        playerRankDao.update(rank);

    }

    @SuppressWarnings("Duplicates")
    private void processNewRank(PlayerAccount account, CreateRankRequest request) {

        PlayerRank rank = new PlayerRank();
        rank.setBtag(account.getBattleTag());
        rank.setGuild(account.getGuildName());

        if (request.getForumId() != null && request.getForumId().isEmpty() == false) {
            rank.setForum(request.getForumId());
        }

        rank.setUpdateDate(account.getLastUpdated()*1000 );

        // rank.setLoginDate(Date.from(Instant.now()).getTime());

        rank.setParagonLevel((int)account.getParagonLevel());
        rank.setParagonLevelHc((int)account.getParagonLevelHardcore());

        playerRankDao.add(rank);

    }


    public void updatePosition() {

        updatePositionBy("paragonLevel");
        updatePositionBy("paragonLevelHc");

        for (int i =0; i <= 30; i++)
        {
            updateSeasonalPositionBy("paragonLevel", i);
            updateSeasonalPositionBy("paragonLevelHc", i);
        }

    }


    @SuppressWarnings("Duplicates")
    private void updateHerosPostion(String rankType, int season) {

        int offset = 0;

        GetHeroRankSeasonalRequest request = new GetHeroRankSeasonalRequest();
        request.setOrderBy("rankValue");
        request.setDirection("desc");
        request.setSeason(season);
        request.setRankType(rankType);

        List<HeroRankSeasonal> heroRanks = heroRankSeasonalDao.getAll(request);

        while (heroRanks.size() > 0) {
            int i = 0;
            for (HeroRankSeasonal rank : heroRanks) {
                int newPosition =  i++ + offset * 40;

                if (rank.getPosition() > 0)
                {
                    rank.setPositionChange(newPosition - rank.getPosition());
                }

                rank.setPosition(newPosition);

                heroRankSeasonalDao.update(rank);
            }

            offset++;
            request.setOffset(offset);
            heroRanks = heroRankSeasonalDao.getAll(request);

        }

    }

    @SuppressWarnings("Duplicates")
    private void updatePositionBy(String field) {

        int offset = 0;

        GetRankingRequest request = new GetRankingRequest();
        request.setOrderBy(field);
        request.setDirection("desc");

        request.setOffset(offset);

        List<PlayerRank> playerRanks = playerRankDao.getAll(request);

        while (playerRanks.size() > 0)
        {
            int i = 0;
            for(PlayerRank rank: playerRanks)
            {
                int newPosition =  i++ + offset * 40;



                if (field.equalsIgnoreCase("paragonLevel")) {

                    if (rank.getPosition() > 0) {
                        rank.setPositionChange(newPosition - rank.getPosition());
                    }

                    rank.setPosition(newPosition);
                }
                else
                {
                    if (rank.getPositionHc() > 0) {
                        rank.setPositionHcChange(newPosition - rank.getPositionHc());
                    }

                    rank.setPositionHc(newPosition);
                }

                playerRankDao.update(rank);
            }

            offset++;
            request.setOffset(offset);
            playerRanks = playerRankDao.getAll(request);

        }
    }

    @SuppressWarnings("Duplicates")
    private void updateSeasonalPositionBy(String field, int season) {

        int offset = 0;

        GetRankingSeasonalRequest request = new GetRankingSeasonalRequest();
        request.setOrderBy(field);
        request.setDirection("desc");

        request.setOffset(offset);
        request.setSeason(season);

        List<PlayerRankSeasonal> playerRanks = playerRankSeasonalDao.getAll(request);

        while (playerRanks.size() > 0)
        {
            int i = 0;
            for(PlayerRankSeasonal rank: playerRanks)
            {
                int newPosition =  i++ + offset * 40;

                if (field.equalsIgnoreCase("paragonLevel")) {

                    if (rank.getPosition() > 0) {
                        rank.setPositionChange(newPosition - rank.getPosition());
                    }

                    rank.setPosition(newPosition);

                }
                else
                {

                    if (rank.getPositionHc() > 0) {
                        rank.setPositionHcChange(newPosition - rank.getPositionHc());
                    }

                    rank.setPositionHc(newPosition);
                }

                playerRankSeasonalDao.update(rank);
            }

            offset++;
            request.setOffset(offset);
            playerRanks = playerRankSeasonalDao.getAll(request);

        }
    }

    @SuppressWarnings("Duplicates")
    public List<HeroRankSeasonal> getHeroSeasonalRanking(GetHeroRankSeasonalRequest request) {

        List<HeroRankSeasonal> heroRankSeasonalList = heroRankSeasonalDao.getAll(request);

        return heroRankSeasonalList;
    }

    public SeasonConfig getSeasonConfig(RankType rankType)
    {
        SeasonConfig seasonConfig = new SeasonConfig();

        if (rankType == RankType.hero) seasonConfig.setMin(1);

        if (rankType == RankType.player) seasonConfig.setMin(this.playerRankSeasonalDao.getMinSeason());

        seasonConfig.setMax(this.client.getCurrentSeason());

        return seasonConfig;
    }



    public List<RankModel> getRankList(RankModelRequest request) {

        List<RankModel> list = new ArrayList<>();

        if (request.getRank().equalsIgnoreCase(RankType.player.name()))
        {
            return (request.getSeasonal().equalsIgnoreCase("false")) ? getPlayerList(request) : getPlayerListSeasonal(request);
        }

        if (request.getRank().equalsIgnoreCase(RankType.hero.name()))
        {
            return getHeroListSeasonal(request);
        }


        return list;
    }

    @SuppressWarnings("Duplicates")
    private List<RankModel> getHeroListSeasonal(RankModelRequest request) {
        List<RankModel> list = new ArrayList<>();

        GetHeroRankSeasonalRequest filter = new GetHeroRankSeasonalRequest();

        setupHeroFilter(filter, request);
        filter.setSeason(request.getSeason());


        List<HeroRankSeasonal> dataList = getHeroSeasonalRanking(filter);

        if (dataList.size() > 0)
        {
            return dataList.stream().map(m->Convert(m, request, m.getSeason())).collect(Collectors.toList());
        }

        return list;
    }


    private void setupHeroFilter(GetHeroRankRequest filter, RankModelRequest request) {

        HeroType type = HeroType.valueOf(request.getHero());

        if (request.getOrderBy().equalsIgnoreCase(OrderByType.rank.name()))
        {
            switch (type)
            {
                case teamTwo:
                    if (request.getHardcore().equalsIgnoreCase("true")) {

                        filter.setRankType(LeaderboardTypes.RiffHardcoreTeamTwo);
                    }
                    else{
                        filter.setRankType(LeaderboardTypes.RiffTeamTwo);
                    }
                    break;
                case teamThree:
                    if (request.getHardcore().equalsIgnoreCase("true")) {
                        filter.setRankType(LeaderboardTypes.RiffHardcoreTeamThree);
                    }
                    else{
                        filter.setRankType(LeaderboardTypes.RiffTeamThree);
                    }
                    break;

                case teamFour:
                    if (request.getHardcore().equalsIgnoreCase("true")) {
                        filter.setRankType(LeaderboardTypes.RiffHardcoreTeamFour);
                    }
                    else{
                        filter.setRankType(LeaderboardTypes.RiffTeamFour);
                    }
                    break;

                case deamonHunter:
                    if (request.getHardcore().equalsIgnoreCase("true")) {
                        filter.setRankType(LeaderboardTypes.RiffHardcoreDeamonHunter);
                    }
                    else{
                        filter.setRankType(LeaderboardTypes.RiffDeamonHunter);
                    }
                    break;
                case barbarian:
                    if (request.getHardcore().equalsIgnoreCase("true")) {
                        filter.setRankType(LeaderboardTypes.RiffHardcoreBarbarian);
                    }
                    else{
                        filter.setRankType(LeaderboardTypes.RiffBarbarian);
                    }
                    break;
                case witchDoctor:
                    if (request.getHardcore().equalsIgnoreCase("true")) {
                        filter.setRankType(LeaderboardTypes.RiffHardcoreWitchDoctor);
                    }
                    else{
                        filter.setRankType(LeaderboardTypes.RiffWitchDoctor);
                    }
                    break;
                case necromancer:
                    if (request.getHardcore().equalsIgnoreCase("true")) {
                        filter.setRankType(LeaderboardTypes.RiffHardcoreNecromancer);
                    }
                    else{
                        filter.setRankType(LeaderboardTypes.RiffNecromancer);
                    }
                    break;
                case wizard:
                    if (request.getHardcore().equalsIgnoreCase("true")) {
                        filter.setRankType(LeaderboardTypes.RiffHardcoreWizard);
                    }
                    else{
                        filter.setRankType(LeaderboardTypes.RiffWizard);
                    }
                    break;
                case monk:
                    if (request.getHardcore().equalsIgnoreCase("true")) {
                        filter.setRankType(LeaderboardTypes.RiffHardcoreMonk);
                    }
                    else{
                        filter.setRankType(LeaderboardTypes.RiffMonk);
                    }
                    break;
                case crusader:
                    if (request.getHardcore().equalsIgnoreCase("true")) {
                        filter.setRankType(LeaderboardTypes.RiffHardcoreCrusader);
                    }
                    else{
                        filter.setRankType(LeaderboardTypes.RiffCrusader);
                    }
                    break;
            }
        }

        filter.setDirection(request.getDirection());
        filter.setOrderBy("rankValue");
        filter.setBtag(request.getBtag());
        filter.setOffset(request.getOffset());

    }

    @SuppressWarnings("Duplicates")
    private List<RankModel> getPlayerListSeasonal(RankModelRequest request) {
        List<RankModel> list = new ArrayList<>();

        GetRankingSeasonalRequest filter = new GetRankingSeasonalRequest();

        setupPlayerFilter(filter, request);
        filter.setSeason(request.getSeason());

        List<PlayerRankSeasonal> dataList = getSeasonalRanking(filter);

        if (dataList.size() > 0)
        {
            return dataList.stream().map(m->Convert(m, request, m.getSeason())).collect(Collectors.toList());
        }

        return list;
    }


    private List<RankModel> getPlayerList(RankModelRequest request) {

        List<RankModel> list = new ArrayList<>();

        GetRankingRequest filter = new GetRankingRequest();

        setupPlayerFilter(filter, request);


        List<PlayerRank> dataList = getRanking(filter);

        if (dataList.size() > 0)
        {
            return dataList.stream().map(m->Convert(m, request)).collect(Collectors.toList());
        }


        return list;
    }

    private void setupPlayerFilter(GetRankingRequest filter, RankModelRequest request) {
        if (request.getOrderBy().equalsIgnoreCase(OrderByType.rank.name()))
        {
            if (request.getHardcore().equalsIgnoreCase("true")) {
                filter.setOrderBy("paragonLevelHc");
            }
            else{
                filter.setOrderBy("paragonLevel");
            }
        }
        else if (request.getOrderBy().equalsIgnoreCase( OrderByType.guild.name()))
        {
            filter.setOrderBy("guild");
        }
        else if (request.getOrderBy().equalsIgnoreCase(OrderByType.position.name()))
        {
            if (request.getHardcore().equalsIgnoreCase("true")) {
                filter.setOrderBy("position");
            }
            else{
                filter.setOrderBy("positionHc");
            }
        }
        else if (request.getOrderBy().equalsIgnoreCase(OrderByType.updated.name()))
        {
            filter.setOrderBy("updateDate");
        }

        filter.setDirection(request.getDirection());
        filter.setBtag(request.getBtag());
        filter.setOffset(request.getOffset());
    }

    @SuppressWarnings("Duplicates")
    private RankModel Convert(BasePlayerRank item, RankModelRequest request )
    {
        RankModel model = new RankModel();

        model.setId(item.getId());
        model.setBtag(item.getBtag());
        model.setGuild(item.getGuild());
        model.setForum(item.getForum());
        model.setUpdateDate(DateUtil.timeToDate(item.getUpdateDate()));
        model.setPostionChange(item.getPositionChange() > 0 ? item.getPositionChange() : 0);

        if (request.getHardcore().equalsIgnoreCase("true")) {

            model.setRank(item.getParagonLevelHc());
            model.setPosition(item.getPositionHc());
            model.setPostionChange(item.getPositionHcChange());
        }
        else {
            model.setRank(item.getParagonLevel());
            model.setPosition(item.getPosition());
            model.setPostionChange(item.getPositionHcChange());
        }

        return model;
    }

    private RankModel Convert(BaseHeroRank item, RankModelRequest request, int season )
    {
        RankModel model = Convert(item, request);
        model.setSeason(season);

        return model;
    }

    @SuppressWarnings("Duplicates")
    private RankModel Convert(BaseHeroRank item, RankModelRequest request ) {
        RankModel model = new RankModel();

        model.setId(item.getId());
        model.setBtag(item.getBtag());
        model.setGuild(item.getGuild());
        model.setForum(item.getForum());
        model.setUpdateDate(DateUtil.timeToDate(item.getUpdateDate()));
        model.setPostionChange(item.getPositionChange() > 0 ? item.getPositionChange() : 0);
        model.setPosition(item.getPosition());
        model.setRank(item.getRankValue());
        model.setRankEu(item.getRankEu());




        return model;
    }

    private RankModel Convert(BasePlayerRank item, RankModelRequest request, int season )
    {
        RankModel model = Convert(item, request);
        model.setSeason(season);

        return model;
    }

}
