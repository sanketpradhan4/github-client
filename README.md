# github-client
Github client for Titania

This Spring Boot application integrates with GitHub's API to provide repository details and list all repositories for a user. 
The application uses RestTemplate to make API calls and secures the connection using a GitHub Personal Access Token (PAT).

Assumptions :
1. Java 11
2. Spring boot 2.7.18
3. Maven 
4. Lombok
5. Actuator
6. SLF4J logger

Add github token in application.properties under github.api.token

APIs exposed/tested on Postman :
1. localhost:8080/github/health
2. localhost:8080/github/repos/{owner}
3. localhost:8080/github/commits/{owner}/{repo}
4. localhost:8080/github/comments/{owner}/{repo}
5. localhost:8080/github/pulls/{owner}/{repo}

Download and follow below steps :

mvn clean
mvn install
mvn spring-boot:run

API researched and used from https://api.github.com/
Assumed models for repository / commits / comment / pullrequest.
These can be used to with Grafana / Prometheus to publish metrics.
