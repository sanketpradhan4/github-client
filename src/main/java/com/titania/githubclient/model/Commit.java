package com.titania.githubclient.model;

import lombok.Data;

@Data
public class Commit {
    private String sha;
    private String message;
    private Committer committer;

    @Data
    public static class Committer {
        private String name;
        private String date;
    }
}
