package com.telran.hometask61solution.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@ToString
public class TopicResponseDto extends BaseTopicDto {

    private String id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime date;

    @Builder(builderMethodName = "topicResponseBuilder")
    public TopicResponseDto(String author, String title, String content, String id, LocalDateTime date){
        super(author,title,content);
        this.id = id;
        this.date = date;
    }
}
