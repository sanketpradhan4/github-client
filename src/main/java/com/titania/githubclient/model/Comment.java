package com.titania.githubclient.model;

import lombok.Data;

@Data
public class Comment {
    private int id;
    private String body;
    private User user;

    @Data
    public static class User {
        private String login;
    }
}
