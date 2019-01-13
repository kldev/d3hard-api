package com.kldev.d3.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateRankRequest extends GetPlayerRequest {

    @JsonProperty
    private String forumId;

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }
}
