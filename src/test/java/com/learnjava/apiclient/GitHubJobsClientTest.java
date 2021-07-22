package com.learnjava.apiclient;

import com.learnjava.domain.github.GitHubPosition;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.learnjava.util.LoggerUtil.log;
import static org.junit.jupiter.api.Assertions.*;

class GitHubJobsClientTest {

    WebClient webClient = WebClient.create("https://jobs.github.com/");
    GitHubJobsClient gitHubJobsClient = new GitHubJobsClient(webClient);

    @Test
    void test(){

        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGithubJobsAPI_withPageNumber(1, "JavaScript");
        log("githubpostions "+gitHubPositions);
        assertTrue(gitHubPositions.size() == 0);

    }
    @Test
    void test1(){

        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsAPI_usingMultipleParameters(List.of(1,2,3), "Java");
        log("githubpostions "+gitHubPositions);
        assertTrue(gitHubPositions.size() == 0);

    }

    @Test
    void test2(){

        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsAPI_usingMultipleParameters_cf(List.of(1,2,3), "Java");
        log("githubpostions "+gitHubPositions);
        assertTrue(gitHubPositions.size() == 0);

    }

    @Test
    void test3(){

        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsAPI_usingMultipleParameters_cf_allOff(List.of(1,2,3), "Java");
        log("githubpostions "+gitHubPositions);
        assertTrue(gitHubPositions.size() == 0);

    }
}