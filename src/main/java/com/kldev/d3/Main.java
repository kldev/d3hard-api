/*  */package com.kldev.d3;

import com.kldev.d3.command.ImportFromForumCommand;
import com.kldev.d3.command.UpdateAllCommand;
import com.kldev.d3.configuration.ServerConfig;
import com.kldev.d3.controller.EchoController;
import com.kldev.d3.controller.RankController;
import com.kldev.d3.healthcheck.AppCheck;
import com.kldev.d3.service.RankService;
import com.kldev.d3.storage.*;
import com.kldev.d3.storage.dao.*;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class Main extends Application<ServerConfig> {


    private final EchoController echoController = new EchoController();
    
    public static void main(String[] args) throws Exception
    {
        new  Main().run(args);
    }


    private final HibernateBundle<ServerConfig> hibernate = new HibernateBundle<ServerConfig>(PlayerRank.class, PlayerRankSeasonal.class, HeroRankSeasonal.class, RankSeason.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ServerConfig configuration) {
            return configuration.getDataSourceFactory();
        }
    };


    @Override
    public void initialize(Bootstrap<ServerConfig> bootstrap) {


        ImportFromForumCommand importFromForum = new ImportFromForumCommand();
        UpdateAllCommand updateAllCommand = new UpdateAllCommand();
        bootstrap.addCommand(importFromForum);
        bootstrap.addCommand(updateAllCommand);
        bootstrap.addBundle(hibernate);


        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
    }

    @Override
    public void run(ServerConfig config, Environment env) throws Exception {

        System.out.println(config.toString());

        final HeroRankSeasonalDao heroRankSeasonalDao = new HeroRankSeasonalDao(hibernate.getSessionFactory());
        final PlayerRankDao playerRankDao = new PlayerRankDao(hibernate.getSessionFactory());
        final PlayerRankSeasonalDao playerRankSeasonalDao = new PlayerRankSeasonalDao(hibernate.getSessionFactory());
        final RankSeasonDao rankSeasonDao = new RankSeasonDao(hibernate.getSessionFactory());


        final RankService rankService = new RankService(heroRankSeasonalDao,
                                    playerRankDao, playerRankSeasonalDao, config.getBlizard(), rankSeasonDao);

        final RankController rankController = new RankController(rankService);


        env.jersey().register(echoController);
        env.jersey().register(rankController);

        env.healthChecks().register("app", new AppCheck(config.getVersion()));
    }
}
