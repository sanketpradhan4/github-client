package com.titania.githubclient.service;

import com.titania.githubclient.model.Comment;
import com.titania.githubclient.model.Commit;
import com.titania.githubclient.model.PullRequest;
import com.titania.githubclient.model.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GitHubService {

    private final RestTemplate restTemplate;
    private final String githubApiUrl;
    private final String githubToken;

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubService.class);

    public boolean isGitHubApiUp() {
        String url = githubApiUrl;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            LOGGER.error("Error reaching GitHub API: {}", e.getMessage());
            return false;
        }
    }

    @Autowired
    public GitHubService(RestTemplateBuilder restTemplateBuilder, @Value("${github.api.url}") String githubApiUrl, @Value("${github.api.token}") String githubToken) {
        this.restTemplate = restTemplateBuilder.build();
        this.githubApiUrl = githubApiUrl;
        this.githubToken = githubToken;
    }

    public List<Commit> getCommits(String owner, String repo) {
        String url = String.format("%s/repos/%s/%s/commits", githubApiUrl, owner, repo);
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        LOGGER.debug("Fetching commits for repo: {}/{}", owner, repo);

        ResponseEntity<Commit[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Commit[].class);
        return Arrays.asList(response.getBody());
    }

    public List<PullRequest> getPullRequests(String owner, String repo) {
        String url = String.format("%s/repos/%s/%s/pulls", githubApiUrl, owner, repo);
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        LOGGER.info("Fetching pull requests for repo: {}/{}", owner, repo);

        ResponseEntity<PullRequest[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, PullRequest[].class);
        return Arrays.asList(response.getBody());
    }

    public List<Comment> getComments(String owner, String repo) {
        String url = String.format("%s/repos/%s/%s/issues/comments", githubApiUrl, owner, repo);
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        LOGGER.debug("Fetching comments for repo: {}/{}", owner, repo);

        ResponseEntity<Comment[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Comment[].class);
        return Arrays.asList(response.getBody());
    }

    public List<Repository> getAllRepositoriesForUser(String owner) {
        String url = String.format("%s/users/%s/repos", githubApiUrl, owner);
        LOGGER.debug("url "+url);
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        try {
            LOGGER.info("Fetching all repositories for user: {}", owner);
            //ResponseEntity<String[]> response= restTemplate.exchange(url, HttpMethod.GET, entity, String[].class);

            ResponseEntity<Repository[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Repository[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            LOGGER.error("Error fetching repositories for user {}: {}", owner, e.getMessage());
            throw new RuntimeException("Failed to fetch repositories for the user.");
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);
        headers.set("Accept", "application/vnd.github.v3+json");
        return headers;
    }
}
