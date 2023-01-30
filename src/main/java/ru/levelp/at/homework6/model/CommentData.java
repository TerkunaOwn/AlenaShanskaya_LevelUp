package ru.levelp.at.homework6.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class CommentData {
    private int id;
    @JsonProperty("post_id")
    private String postId;
    private String name;
    private String email;
    private String body;
}
