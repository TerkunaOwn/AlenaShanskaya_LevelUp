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
import ru.levelp.at.homework6.model.CommentData;
import ru.levelp.at.homework6.model.CreateCommentDataRequest;
import ru.levelp.at.homework6.service.CommentsRequest;

class CommentsServiceTest extends BaseServiceTest {

    static Stream<Arguments> commentParametersDataProvider() {

        var comments = getComments();
        var randomInt = new Random().nextInt(5);

        return Stream.of(
            Arguments.of(Map.of("post_id", comments[randomInt].getPostId())),
            Arguments.of(Map.of("name", comments[randomInt].getName())),
            Arguments.of(Map.of("email", comments[randomInt].getEmail())),
            Arguments.of(Map.of("body", comments[randomInt].getBody()))
        );
    }

    static Stream<Arguments> parametersForCreatingCommentsDataProvider() {

        var posts = getPosts();
        var faker = new Faker();

        List<Arguments> commentParameters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            commentParameters.add(Arguments.of(
                Integer.toString(posts[i].getId()),
                faker.name().firstName() + " " + faker.name().lastName(),
                faker.internet().emailAddress(),
                faker.lorem().sentence(5)
            ));
        }

        return commentParameters.stream();
    }

    private CommentData[] getCommentsByParameters(Map<String, String> params) {
        return new CommentsRequest(requestSpecification())
            .getCommentsByParameters(params)
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(CommentData[].class);
    }

    @Test
    void getCommentsTest() {
        var rsBody = new CommentsRequest(requestSpecification())
            .getComments()
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(CommentData[].class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rsBody).extracting(CommentData::getId).isNotNull();
            softly.assertThat(rsBody).extracting(CommentData::getPostId).isNotNull();
            softly.assertThat(rsBody).extracting(CommentData::getName).isNotNull();
            softly.assertThat(rsBody).extracting(CommentData::getEmail).isNotNull();
            softly.assertThat(rsBody).extracting(CommentData::getBody).isNotNull();
        });
    }

    @ParameterizedTest
    @MethodSource("commentParametersDataProvider")
    void getCommentByParameterTest(Map<String, String> parameters) {

        if (parameters.containsKey("post_id")) {
            var rsBody = getCommentsByParameters(parameters);
            assertThat(rsBody[0].getPostId()).isEqualTo(parameters.get("post_id"));
        } else if (parameters.containsKey("name")) {
            var rsBody = getCommentsByParameters(parameters);
            assertThat(rsBody[0].getName()).isEqualTo(parameters.get("name"));
        } else if (parameters.containsKey("email")) {
            var rsBody = getCommentsByParameters(parameters);
            assertThat(rsBody[0].getEmail()).isEqualTo(parameters.get("email"));
        } else if (parameters.containsKey("body")) {
            var rsBody = getCommentsByParameters(parameters);
            assertThat(rsBody[0].getBody()).isEqualTo(parameters.get("body"));
        }
    }

    @ParameterizedTest
    @MethodSource("commentIdDataProvider")
    void getCommentById(int id) {
        var rsBody = new CommentsRequest(requestSpecification())
            .getCommentById(id)
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(CommentData.class);

        assertThat(rsBody.getId()).isEqualTo(id);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, -99, 10101, 9999999})
    void getNonexistentCommentByIdTest(int id) {
        new CommentsRequest(requestSpecification())
            .getCommentById(id)
            .then()
            .spec(responseSpecWithCode404());
    }

    @ParameterizedTest
    @MethodSource("parametersForCreatingCommentsDataProvider")
    void createCommentTest(String postId, String name, String email, String body) {
        var rqBody = CreateCommentDataRequest.builder()
                                             .postId(postId)
                                             .name(name)
                                             .email(email)
                                             .body(body)
                                             .build();

        CommentData rsBody = new CommentsRequest(requestSpecification())
            .createComment(rqBody)
            .then()
            .spec(responseSpecWithCode201())
            .extract()
            .as(CommentData.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rsBody.getId()).isNotNull();
            softly.assertThat(rsBody.getPostId()).isEqualTo(rqBody.getPostId());
            softly.assertThat(rsBody.getName()).isEqualTo(rqBody.getName());
            softly.assertThat(rsBody.getEmail()).isEqualTo(rqBody.getEmail());
            softly.assertThat(rsBody.getBody()).isEqualTo(rqBody.getBody());
        });
    }

    @ParameterizedTest
    @MethodSource("parametersForCreatingCommentsDataProvider")
    void updateCommentTest(String postId, String name, String email, String body) {
        var comments = getComments();
        var commentId = comments[new Random().nextInt(5)].getId();

        var rqBody = CreateCommentDataRequest.builder()
                                             .postId(postId)
                                             .name(name)
                                             .email(email)
                                             .body(body)
                                             .build();

        CommentData rsBody = new CommentsRequest(requestSpecification())
            .updateCommentById(rqBody, commentId)
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(CommentData.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rsBody.getId()).isEqualTo(commentId);
            softly.assertThat(rsBody.getPostId()).isEqualTo(rqBody.getPostId());
            softly.assertThat(rsBody.getName()).isEqualTo(rqBody.getName());
            softly.assertThat(rsBody.getEmail()).isEqualTo(rqBody.getEmail());
            softly.assertThat(rsBody.getBody()).isEqualTo(rqBody.getBody());
        });
    }

    @ParameterizedTest
    @MethodSource("parametersForCreatingCommentsDataProvider")
    void updateNonexistentCommentTest(String postId, String name, String email, String body) {
        var commentId = new Random().nextInt(888888 + 111111);

        var rqBody = CreateCommentDataRequest.builder()
                                             .postId(postId)
                                             .name(name)
                                             .email(email)
                                             .body(body)
                                             .build();

        new CommentsRequest(requestSpecification())
            .updateCommentById(rqBody, commentId)
            .then()
            .spec(responseSpecWithCode404());
    }

    @ParameterizedTest
    @MethodSource("commentIdDataProvider")
    void deleteCommentTest(int id) {
        new CommentsRequest(requestSpecification())
            .deleteCommentById(id)
            .then()
            .spec(responseSpecWithCode204());
    }
}
