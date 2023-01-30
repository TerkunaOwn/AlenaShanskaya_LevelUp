package ru.levelp.at.levelp.at.homework6;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.levelp.at.homework6.model.CreatePostDataRequest;
import ru.levelp.at.homework6.model.PostData;
import ru.levelp.at.homework6.service.PostsRequest;

class PostsServiceTest extends BaseServiceTest {

    static Stream<Arguments> postParametersDataProvider() {

        var posts = getPosts();
        var randomInt = new Random().nextInt(5);

        return Stream.of(
            Arguments.of(Map.of("user_id", posts[randomInt].getUserId())),
            Arguments.of(Map.of("title", posts[randomInt].getTitle())),
            Arguments.of(Map.of("body", posts[randomInt].getBody()))
        );
    }

    static Stream<Arguments> parametersForCreatingPostsDataProvider() {

        var users = getUsers();
        var faker = new Faker();

        List<Arguments> postParameters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            postParameters.add(Arguments.of(
                Integer.toString(users[i].getId()),
                faker.lorem().sentence(5),
                faker.lorem().paragraph()
            ));
        }

        return postParameters.stream();
    }

    private PostData[] getPostsByParameters(Map<String, String> params) {
        return new PostsRequest(requestSpecification())
            .getPostsByParameters(params)
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(PostData[].class);
    }

    @Test
    void getPostsTest() {
        var rsBody = new PostsRequest(requestSpecification())
            .getPosts()
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(PostData[].class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rsBody).extracting(PostData::getId).isNotNull();
            softly.assertThat(rsBody).extracting(PostData::getUserId).isNotNull();
            softly.assertThat(rsBody).extracting(PostData::getTitle).isNotNull();
            softly.assertThat(rsBody).extracting(PostData::getBody).isNotNull();
        });
    }

    @ParameterizedTest
    @MethodSource("postParametersDataProvider")
    void getPostByParameterTest(Map<String, String> parameters) {

        if (parameters.containsKey("user_id")) {
            var rsBody = getPostsByParameters(parameters);
            assertThat(rsBody[0].getUserId()).isEqualTo(parameters.get("user_id"));
        } else if (parameters.containsKey("title")) {
            var rsBody = getPostsByParameters(parameters);
            assertThat(rsBody[0].getTitle()).isEqualTo(parameters.get("title"));
        } else if (parameters.containsKey("body")) {
            var rsBody = getPostsByParameters(parameters);
            assertThat(rsBody[0].getBody()).isEqualTo(parameters.get("body"));
        }
    }

    @ParameterizedTest
    @MethodSource("postIdDataProvider")
    void getPostById(int id) {
        var rsBody = new PostsRequest(requestSpecification())
            .getPostById(id)
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(PostData.class);

        assertThat(rsBody.getId()).isEqualTo(id);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 11, 77777, 1000000})
    void getNonexistentPostByIdTest(int id) {
        new PostsRequest(requestSpecification())
            .getPostById(id)
            .then()
            .spec(responseSpecWithCode404());
    }

    @ParameterizedTest
    @MethodSource("parametersForCreatingPostsDataProvider")
    void createPostTest(String userId, String title, String body) {
        var rqBody = CreatePostDataRequest.builder()
                                          .userId(userId)
                                          .title(title)
                                          .body(body)
                                          .build();

        PostData rsBody = new PostsRequest(requestSpecification())
            .createPost(rqBody)
            .then()
            .spec(responseSpecWithCode201())
            .extract()
            .as(PostData.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rsBody.getId()).isNotNull();
            softly.assertThat(rsBody.getUserId()).isEqualTo(rqBody.getUserId());
            softly.assertThat(rsBody.getTitle()).isEqualTo(rqBody.getTitle());
            softly.assertThat(rsBody.getBody()).isEqualTo(rqBody.getBody());
        });
    }

    @ParameterizedTest
    @MethodSource("parametersForCreatingPostsDataProvider")
    void updatePostTest(String userId, String title, String body) {
        var posts = getPosts();
        var postId = posts[new Random().nextInt(5)].getId();

        var rqBody = CreatePostDataRequest.builder()
                                          .userId(userId)
                                          .title(title)
                                          .body(body)
                                          .build();

        PostData rsBody = new PostsRequest(requestSpecification())
            .updatePostById(rqBody, postId)
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(PostData.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rsBody.getId()).isEqualTo(postId);
            softly.assertThat(rsBody.getUserId()).isEqualTo(rqBody.getUserId());
            softly.assertThat(rsBody.getTitle()).isEqualTo(rqBody.getTitle());
            softly.assertThat(rsBody.getBody()).isEqualTo(rqBody.getBody());
        });
    }

    @ParameterizedTest
    @MethodSource("parametersForCreatingPostsDataProvider")
    void updateNonexistentPostTest(String userId, String title, String body) {
        var postId = new Random().nextInt(888888 + 111111);

        var rqBody = CreatePostDataRequest.builder()
                                          .userId(userId)
                                          .title(title)
                                          .body(body)
                                          .build();

        new PostsRequest(requestSpecification())
            .updatePostById(rqBody, postId)
            .then()
            .spec(responseSpecWithCode404());
    }

    @ParameterizedTest
    @MethodSource("postIdDataProvider")
    void deletePostTest(int id) {
        new PostsRequest(requestSpecification())
            .deletePostById(id)
            .then()
            .spec(responseSpecWithCode204());
    }
}
