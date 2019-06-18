package com.telran.hometask61solution.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class BaseTopicDto {
    private String author;
    private String title;
    private String content;
}
