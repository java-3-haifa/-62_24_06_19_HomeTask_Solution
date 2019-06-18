package com.telran.hometask61solution.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class FullTopicDto extends TopicResponseDto {
    private List<FullCommentDto> comments;

    @Builder(builderMethodName = "fullTopicBuilder")
    public FullTopicDto(String author, String title, String content, String id, LocalDateTime date, List<FullCommentDto> comments){
        super(author, title, content, id, date);
        this.comments = comments;
    }
}
