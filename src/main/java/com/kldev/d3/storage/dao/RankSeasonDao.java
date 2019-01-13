package com.kldev.d3.storage.dao;

import com.kldev.d3.storage.RankSeason;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class RankSeasonDao extends AbstractDAO<RankSeason> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public RankSeasonDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public RankSeason findBySeason(int season)
    {
        Query query = query("from RankSeason WHERE season = :season");
        query.setParameter("season", season);
        query.setMaxResults(1);

        return uniqueResult(query);
    }

    public void add(RankSeason rank)
    {
        this.currentSession().save(rank);
    }

    public int maxSeason()
    {
        Query query = query("from RankSeason ORDER BY season desc");
        query.setMaxResults(1);

        List<RankSeason> seasonList = list(query);

        if (seasonList != null && seasonList.size() > 0)
        {
            return seasonList.get(0).getSeason();
        }

        return 0;

    }
}
