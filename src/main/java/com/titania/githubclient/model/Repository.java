package com.titania.githubclient.model;

import lombok.Data;

@Data
public class Repository {
    private String name;
    private String fullName;
    private String description;
    private boolean fork;
    private int stargazersCount;
    private int watchersCount;
    private int forksCount;
    private String language;
    private String htmlUrl;
    private Owner owner;

    @Data
    public static class Owner {
        private String login;
        private String htmlUrl;
    }
}
