package com.telran.hometask61solution.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@ToString
public class FullCommentDto extends BaseCommentDto {
    private String id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime date;

    @Builder(builderMethodName = "fullCommentBuilder")
    public FullCommentDto(String author, String message, String id, LocalDateTime date){
        super(author, message);
        this.id = id;
        this.date = date;
    }
}
