package ru.levelp.at.homework6.service;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import ru.levelp.at.homework6.model.CreateCommentDataRequest;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CommentsRequest {
    private static final String COMMENTS_ENDPOINT = "/comments";

    private final RequestSpecification requestSpecification;

    public CommentsRequest(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response getComments() {
        return given()
            .spec(requestSpecification)
            .when()
            .get(COMMENTS_ENDPOINT)
            .andReturn();
    }

    public Response createComment(CreateCommentDataRequest body) {
        return given()
            .spec(requestSpecification)
            .body(body)
            .when()
            .post(COMMENTS_ENDPOINT)
            .andReturn();
    }

    public Response getCommentsByParameters(final Map<String, String> parameters) {
        return given()
            .spec(requestSpecification)
            .queryParams(parameters)
            .when()
            .get(COMMENTS_ENDPOINT)
            .andReturn();
    }

    public Response getCommentById(int id) {
        return given()
            .spec(requestSpecification)
            .when()
            .get(COMMENTS_ENDPOINT + "/" + id)
            .andReturn();
    }

    public Response updateCommentById(CreateCommentDataRequest body, int id) {
        return given()
            .spec(requestSpecification)
            .body(body)
            .when()
            .put(COMMENTS_ENDPOINT + "/" + id)
            .andReturn();
    }

    public Response deleteCommentById(int id) {
        return given()
            .spec(requestSpecification)
            .when()
            .delete(COMMENTS_ENDPOINT + "/" + id)
            .andReturn();
    }
}
