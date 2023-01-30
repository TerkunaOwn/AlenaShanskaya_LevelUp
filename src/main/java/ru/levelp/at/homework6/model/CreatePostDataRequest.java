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

public class CreatePostDataRequest {

    @JsonProperty("user_id")
    private String userId;
    private String title;
    private String body;
}
