package com.kldev.d3.storage.dao;

import com.kldev.d3.controller.model.GetRankingSeasonalRequest;
import com.kldev.d3.storage.BasePlayerRank;
import com.kldev.d3.storage.PlayerRankSeasonal;
import com.kldev.d3.util.JsonUtil;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class PlayerRankSeasonalDao extends AbstractDAO<PlayerRankSeasonal> {

    private static final Logger logger = LoggerFactory.getLogger(PlayerRankSeasonal.class);

    public PlayerRankSeasonalDao(SessionFactory factory) {
        super(factory);
    }

    /*
    public void deleteAllByBtag(String btag)
    {
        Query query =   this.currentSession().createQuery("DELETE FROM PlayerRankSeasonal WHERE btag = :btag"); // untyped query
        query.setParameter("btag", btag);

        query.executeUpdate();

    }
*/


    public int getMinSeason()
    {
        Query query = this.currentSession().createQuery("SELECT min(season) FROM PlayerRankSeasonal WHERE season > 0");

        Object obj = query.getSingleResult();

        return (int)obj;

    }

    public void deleteAllByBtagAndSeason(String btag, int season)
    {
        Query query =   this.currentSession().createQuery("DELETE FROM PlayerRankSeasonal WHERE btag = :btag AND season = :season"); // untyped query
        query.setParameter("btag", btag);
        query.setParameter("season", season);

        query.executeUpdate();
    }

    public void update(PlayerRankSeasonal rank)
    {
        this.currentSession().update(rank);
    }

    public String add(PlayerRankSeasonal rank)
    {
        String id = UUID.randomUUID().toString(); // maybe this should be check against database
        rank.setId(id);

        this.currentSession().save(rank);

        return id;
    }

    @SuppressWarnings("Duplicates")
    public List<PlayerRankSeasonal> getAll(GetRankingSeasonalRequest request) {

        logger.debug("PlayerRankSeasonalDao.getAll");
        logger.debug(JsonUtil.toJsonString(request));

        if (request.getSeason() < 0) request.setSeason(0);
        if (request.getOffset() < 0) request.setOffset(0);

        if (request.getOrderBy() == null || request.getOrderBy().isEmpty() == true) {
            throw new IllegalArgumentException("orderBy can be empty");
        }

        String querySQL = " FROM PlayerRankSeasonal WHERE season = :season ";

        if (request.getBtag() != null && request.getBtag().isEmpty() == false) {
            querySQL += " AND btag LIKE :btag ";
        }


        querySQL = QueryUtil.setupOrder(querySQL, request, BasePlayerRank.class);

        Query query = query(querySQL);
        query.setMaxResults(40);
        query.setFirstResult(request.getOffset() * 40);
        query.setParameter("season", request.getSeason());

        if (request.getBtag() != null && request.getBtag().isEmpty() == false) {
            query.setParameter("btag", "%" + request.getBtag() + "%");
        }

        logger.debug(querySQL);

        return list(query);
    }
}
