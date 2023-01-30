package ru.levelp.at.homework6.service;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import ru.levelp.at.homework6.model.CreatePostDataRequest;

public class PostsRequest {
    private static final String POSTS_ENDPOINT = "/posts";

    private final RequestSpecification requestSpecification;

    public PostsRequest(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response getPosts() {
        return given()
            .spec(requestSpecification)
            .when()
            .get(POSTS_ENDPOINT)
            .andReturn();
    }

    public Response createPost(CreatePostDataRequest body) {
        return given()
            .spec(requestSpecification)
            .body(body)
            .when()
            .post(POSTS_ENDPOINT)
            .andReturn();
    }

    public Response getPostsByParameters(final Map<String, String> parameters) {
        return given()
            .spec(requestSpecification)
            .queryParams(parameters)
            .when()
            .get(POSTS_ENDPOINT)
            .andReturn();
    }

    public Response getPostById(int id) {
        return given()
            .spec(requestSpecification)
            .when()
            .get(POSTS_ENDPOINT + "/" + id)
            .andReturn();
    }

    public Response updatePostById(CreatePostDataRequest body, int id) {
        return given()
            .spec(requestSpecification)
            .body(body)
            .when()
            .put(POSTS_ENDPOINT + "/" + id)
            .andReturn();
    }

    public Response deletePostById(int id) {
        return given()
            .spec(requestSpecification)
            .when()
            .delete(POSTS_ENDPOINT + "/" + id)
            .andReturn();
    }
}
