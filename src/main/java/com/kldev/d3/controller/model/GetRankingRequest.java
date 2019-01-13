package com.kldev.d3.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetRankingRequest extends BaseRequest implements IOrderByRequest {

    @JsonProperty
    private int offset;

    @JsonProperty
    private String btag;

    @JsonProperty
    private String orderBy;

    @JsonProperty
    private String direction;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getBtag() {
        return btag;
    }

    public void setBtag(String btag) {
        this.btag = btag;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
