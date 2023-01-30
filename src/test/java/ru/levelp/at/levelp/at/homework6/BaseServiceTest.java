package ru.levelp.at.levelp.at.homework6;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.hamcrest.Matchers;
import ru.levelp.at.homework6.model.CommentData;
import ru.levelp.at.homework6.model.PostData;
import ru.levelp.at.homework6.model.UserData;
import ru.levelp.at.homework6.service.CommentsRequest;
import ru.levelp.at.homework6.service.PostsRequest;
import ru.levelp.at.homework6.service.UsersRequest;

public class BaseServiceTest {

    private static final String ACCESS_TOKEN = "770b5c5f94ecb92e78dfa6e0ec536be8b1fd2a672ca001ccdafe121f6cbb978a";

    static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
            .setBaseUri("https://gorest.co.in")
            .setBasePath("public/v2")
            .setContentType(ContentType.JSON)
            .setAuth(RestAssured.oauth2(ACCESS_TOKEN))
            .log(LogDetail.ALL)
            .build();
    }

    static ResponseSpecification responseSpecWithCode200() {
        return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(200)
            .build();
    }

    static ResponseSpecification responseSpecWithCode201() {
        return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(201)
            .build();
    }

    static ResponseSpecification responseSpecWithCode204() {
        return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(204)
            .build();
    }

    static ResponseSpecification responseSpecWithCode404() {
        return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(404)
            .expectBody("message", Matchers.equalTo("Resource not found"))
            .build();
    }

    public static UserData[] getUsers() {
        return new UsersRequest(requestSpecification())
            .getUsers()
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(UserData[].class);
    }

    public static PostData[] getPosts() {
        return new PostsRequest(requestSpecification())
            .getPosts()
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(PostData[].class);
    }

    public static CommentData[] getComments() {
        return new CommentsRequest(requestSpecification())
            .getComments()
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(CommentData[].class);
    }

    public static Stream<Integer> userIdDataProvider() {
        var users = getUsers();
        List<Integer> userIds = new ArrayList<>();
        for (UserData user : users) {
            userIds.add(user.getId());
        }

        return Stream.of(userIds.get(0), userIds.get(1), userIds.get(2), userIds.get(3));
    }

    public static Stream<Integer> postIdDataProvider() {
        var posts = getPosts();
        List<Integer> postIds = new ArrayList<>();
        for (PostData post : posts) {
            postIds.add(post.getId());
        }

        return Stream.of(postIds.get(0), postIds.get(1), postIds.get(2), postIds.get(3));
    }

    public static Stream<Integer> commentIdDataProvider() {
        var comments = getComments();
        List<Integer> commentIds = new ArrayList<>();
        for (CommentData comment : comments) {
            commentIds.add(comment.getId());
        }

        return Stream.of(commentIds.get(0), commentIds.get(1), commentIds.get(2), commentIds.get(3));
    }
}
