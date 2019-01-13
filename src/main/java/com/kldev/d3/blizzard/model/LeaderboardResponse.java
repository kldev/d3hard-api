package com.kldev.d3.blizzard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardResponse {

    @JsonProperty("row")
    private List<LeaderboardItem> rowList = new ArrayList<>();

    public List<LeaderboardItem> getRowList() {
        return rowList;
    }

    public void setRowList(List<LeaderboardItem> rowList) {
        this.rowList = rowList;
    }
}
