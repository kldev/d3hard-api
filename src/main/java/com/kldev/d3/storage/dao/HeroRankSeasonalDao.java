package com.kldev.d3.storage.dao;

import com.kldev.d3.controller.model.GetHeroRankSeasonalRequest;
import com.kldev.d3.storage.BaseHeroRank;
import com.kldev.d3.storage.HeroRankSeasonal;
import com.kldev.d3.util.JsonUtil;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class HeroRankSeasonalDao extends AbstractDAO<HeroRankSeasonal> {

    private static final Logger logger = LoggerFactory.getLogger(HeroRankSeasonalDao.class);

    public HeroRankSeasonalDao(SessionFactory factory) {
        super(factory);
    }

    public String addOrUpdate(HeroRankSeasonal rank) {
        if (rank.getId() == null || rank.getId().isEmpty()) {
            String id = UUID.randomUUID().toString();
            rank.setId(id);
        }

        this.currentSession().save(rank);

        return rank.getId();
    }

    public void update(HeroRankSeasonal rank)
    {
        this.currentSession().update(rank);
    }

    public  HeroRankSeasonal getByBtagAndSeason(String btag, int season, String rankType) {
        Query query =   query("FROM HeroRankSeasonal WHERE btag = :btag and season = :season AND rankType = :rankType"); // untyped query
        query.setParameter("btag", btag);
        query.setParameter("season", season);
        query.setParameter("rankType", rankType);

        List<HeroRankSeasonal> list = query.list();
        if (list.size()> 0) return list.get(0);

        return null;

    }



    @SuppressWarnings("Duplicates")
    public List<HeroRankSeasonal> getAll(GetHeroRankSeasonalRequest request)
    {
        logger.debug("HeroRankSeasonalDao.getAll");
        logger.debug(JsonUtil.toJsonString(request));

        if (request.getOffset() < 0)
        {
            request.setOffset(0);
        }

        if (request.getOrderBy() == null || request.getOrderBy().isEmpty() == true) {
            throw new IllegalArgumentException("orderBy can not be empty");
        }

        if (request.getRankType() == null || request.getRankType().isEmpty() == true) {
            throw new IllegalArgumentException("rankType can not be empty");
        }

        String querySQL = " FROM HeroRankSeasonal ";
        querySQL += " WHERE season = :season AND rankType = :rankType";


        if (request.getBtag() != null && request.getBtag().isEmpty() == false)
        {
            querySQL += " AND btag LIKE :btag ";
        }

        querySQL = QueryUtil.setupOrder(querySQL, request, BaseHeroRank.class);


        Query query = query(querySQL);


        if (request.getBtag() != null && request.getBtag().isEmpty() == false)
        {
            query.setParameter("btag", "%" + request.getBtag() + "%");
        }

        query.setParameter("season", request.getSeason());
        query.setParameter("rankType", request.getRankType());
        query.setMaxResults(40);
        query.setFirstResult(request.getOffset()*40);

        logger.debug(querySQL);

        return list(query);

    }

}
