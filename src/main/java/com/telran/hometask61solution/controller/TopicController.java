package com.telran.hometask61solution.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.hometask61solution.controller.dto.*;
import com.telran.hometask61solution.repository.TopicRepository;
import com.telran.hometask61solution.repository.entity.CommentEntity;
import com.telran.hometask61solution.repository.entity.TopicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class TopicController {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    TopicRepository repository;

    @PostMapping("topic")
    public ResponseEntity<String> addTopic(@RequestBody BaseTopicDto topic){
        TopicResponseDto response = TopicResponseDto.topicResponseBuilder()
                .author(topic.getAuthor())
                .title(topic.getTitle())
                .content(topic.getContent())
                .date(LocalDateTime.now())
                .id(UUID.randomUUID().toString())
                .build();

        try {
            repository.addTopic(map(response));
            return ResponseEntity.ok(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Parse error",e);
        }
    }

    @GetMapping("topic")
    public ResponseEntity<String> getAllTopics(){
        List<FullTopicDto> response = StreamSupport.stream(repository.getAllTopics().spliterator(),false)
                .map(this::map)
                .collect(Collectors.toList());
        try {
            return ResponseEntity.ok(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Parse error",e);
        }
    }

    @DeleteMapping("topic/{topicId}")
    public ResponseEntity<String> removeTopicById(@PathVariable("topicId") String topicId){
        repository.removeTopic(UUID.fromString(topicId));
        SuccessResponseDto response = new SuccessResponseDto("Topic with id: " + topicId + " was removed!");
        try {
            return ResponseEntity.ok(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Parse error",e);
        }
    }

    private TopicEntity map(TopicResponseDto dto){
        return new TopicEntity(UUID.fromString(dto.getId()),
                dto.getAuthor(),
                dto.getTitle(),
                dto.getContent(),
                dto.getDate(),
                null);
    }

    private FullTopicDto map(TopicEntity topicEntity){
        return FullTopicDto.fullTopicBuilder()
                .author(topicEntity.getAuthor())
                .title(topicEntity.getTitle())
                .content(topicEntity.getContent())
                .comments(map(topicEntity.getComments()))
                .id(topicEntity.getId().toString())
                .date(topicEntity.getDate())
                .build();
    }

    private List<FullCommentDto> map(List<CommentEntity> list){
        return list.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private FullCommentDto map(CommentEntity entity){
        return FullCommentDto.fullCommentBuilder()
                .id(entity.getId().toString())
                .date(entity.getDate())
                .author(entity.getAuthor())
                .message(entity.getMessage())
                .build();
    }
}
