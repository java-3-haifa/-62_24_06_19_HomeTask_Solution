package com.telran.hometask61solution.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
public class AddCommentDto extends BaseCommentDto {
    private String topicId;
}
