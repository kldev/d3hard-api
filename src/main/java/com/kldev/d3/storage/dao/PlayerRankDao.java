package com.kldev.d3.storage.dao;

import com.kldev.d3.controller.model.GetRankingRequest;
import com.kldev.d3.storage.BasePlayerRank;
import com.kldev.d3.storage.PlayerRank;
import com.kldev.d3.util.JsonUtil;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerRankDao extends AbstractDAO<PlayerRank> {

    private static final Logger logger = LoggerFactory.getLogger(PlayerRankDao.class);

    public PlayerRankDao(SessionFactory factory) {
        super(factory);
    }

    public PlayerRank findByBtag(String btag)
    {
        Query query = query("from PlayerRank WHERE bTag = :btag");
        query.setParameter("btag", btag);
        query.setMaxResults(1);

        return uniqueResult(query);
    }

    public String add(PlayerRank rank)
    {
        String id = UUID.randomUUID().toString();
        rank.setId(id);

        this.currentSession().save(rank);

        return id;
    }

    public void update(PlayerRank rank)
    {
        this.currentSession().update(rank);
    }

    @SuppressWarnings("Duplicates")
    public List<PlayerRank> getAll(GetRankingRequest request)
    {
        logger.debug("PlayerRankDao.getAll");
        logger.debug(JsonUtil.toJsonString(request));

        if (request.getOffset() < 0)
        {
            request.setOffset(0);
        }

        if (request.getOrderBy() == null || request.getOrderBy().isEmpty() == true) {
            throw new IllegalArgumentException("orderBy can be empty");
        }

        String querySQL = " FROM PlayerRank ";


        if (request.getBtag() != null && request.getBtag().isEmpty() == false)
        {
            querySQL += " WHERE btag LIKE :btag ";
        }


        querySQL = QueryUtil.setupOrder(querySQL, request, BasePlayerRank.class);

        logger.info("SQL:" + querySQL);

        Query query = query(querySQL);


        if (request.getBtag() != null && request.getBtag().isEmpty() == false)
        {
            query.setParameter("btag", "%" + request.getBtag() + "%");
        }

        query.setMaxResults(40);
        query.setFirstResult(request.getOffset()*40);

        logger.debug(querySQL);


        return list(query);
    }
}
