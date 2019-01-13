package com.kldev.d3.controller;

import com.codahale.metrics.annotation.Timed;
import com.kldev.d3.controller.model.*;
import com.kldev.d3.service.RankService;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/rank")
public class RankController {

    private static final Logger logger = LoggerFactory.getLogger(RankController.class);
    private RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @Timed
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    @UnitOfWork
    public String createRank(CreateRankRequest request)
    {
        return rankService.createRank(request);
    }

    @POST
    @Timed
    @UnitOfWork
    @Path("/updatePosition")
    public String updatePosition(BaseRequest request){

        rankService.updatePosition();

        return "update position - completed ";
    }

    @POST
    @Timed
    @UnitOfWork
    @Path("/updateHeros")
    public String updateHerosRanks(CreateHeroRankRequest request){

        return rankService.updateHerosRanks(request);

    }


    @Timed
    @UnitOfWork
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/seasonalConfig")
    public SeasonConfig getSeasonConfig(GetSeasonConfigRequest request)
    {
        return rankService.getSeasonConfig(request.getRankType());
    }

    @Timed
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    @UnitOfWork
    public List<RankModel> getRanking(RankModelRequest request)
    {
        try {
            return rankService.getRankList(request);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            return new ArrayList<>();
        }
    }

}
