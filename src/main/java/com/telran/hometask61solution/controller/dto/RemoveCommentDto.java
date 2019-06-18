package com.telran.hometask61solution.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class RemoveCommentDto {
    private String topicId;
    private String commentId;
}
