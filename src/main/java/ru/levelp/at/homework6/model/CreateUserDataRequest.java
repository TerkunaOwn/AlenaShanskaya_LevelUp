package ru.levelp.at.homework6.model;

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
public class CreateUserDataRequest {

    private String name;
    private String email;
    private String gender;
    private String status;
}
