package com.titania.githubclient.model;

import lombok.Data;

@Data
public class PullRequest {
    private int id;
    private String title;
    private String state;
    private User user;

    @Data
    public static class User {
        private String login;
    }
}
