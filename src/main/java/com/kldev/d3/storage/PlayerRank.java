package com.kldev.d3.storage;


import javax.persistence.*;


@Entity
@Table(name = "player_rank",
indexes = {
        @Index(name = "player_rank__btag", columnList = "btag")
})
public class PlayerRank  extends BasePlayerRank {

}
