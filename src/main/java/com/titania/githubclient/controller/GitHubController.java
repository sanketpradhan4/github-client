package com.titania.githubclient.controller;

import com.titania.githubclient.model.Commit;
import com.titania.githubclient.model.Comment;
import com.titania.githubclient.model.PullRequest;
import com.titania.githubclient.model.Repository;
import com.titania.githubclient.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github")
public class GitHubController {

    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> checkGitHubApiStatus() {
        boolean isUp = gitHubService.isGitHubApiUp();
        if (isUp) {
            return ResponseEntity.ok("GitHub API is up and running.");
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("GitHub API is down.");
        }
    }

    @GetMapping("/commits/{owner}/{repo}")
    public ResponseEntity<List<Commit>> getCommits(@PathVariable String owner, @PathVariable String repo) {
        List<Commit> commits = gitHubService.getCommits(owner, repo);
        return ResponseEntity.ok(commits);
    }

    @GetMapping("/pulls/{owner}/{repo}")
    public ResponseEntity<List<PullRequest>> getPullRequests(@PathVariable String owner, @PathVariable String repo) {
        List<PullRequest> pullRequests = gitHubService.getPullRequests(owner, repo);
        return ResponseEntity.ok(pullRequests);
    }

    @GetMapping("/comments/{owner}/{repo}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable String owner, @PathVariable String repo) {
        List<Comment> comments = gitHubService.getComments(owner, repo);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/repos/{owner}")
    public ResponseEntity<List<Repository>> getAllRepositoriesForUser(@PathVariable String owner) {
        List<Repository> repositories = gitHubService.getAllRepositoriesForUser(owner);
        return ResponseEntity.ok(repositories);
    }
}
