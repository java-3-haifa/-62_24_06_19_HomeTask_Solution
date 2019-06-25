package com.telran.hometask61solution.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.hometask61solution.controller.dto.*;
import com.telran.hometask61solution.repository.TopicRepository;
import com.telran.hometask61solution.repository.entity.CommentEntity;
import com.telran.hometask61solution.repository.entity.TopicEntity;
import com.telran.hometask61solution.repository.exceptions.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public TopicResponseDto addTopic(@RequestBody BaseTopicDto topic){
        TopicResponseDto response = TopicResponseDto.topicResponseBuilder()
                .author(topic.getAuthor())
                .title(topic.getTitle())
                .content(topic.getContent())
                .date(LocalDateTime.now())
                .id(UUID.randomUUID().toString())
                .build();
        try {
            repository.addTopic(map(response));
            return  response;
        }catch (RepositoryException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }
    }

    @GetMapping("topic")
    public List<FullTopicDto> getAllTopics(){
        return StreamSupport.stream(repository.getAllTopics().spliterator(),false)
                .map(this::map)
                .collect(Collectors.toList());
    }

    @DeleteMapping("topic/{topicId}")
    public SuccessResponseDto removeTopicById(@PathVariable("topicId") String topicId){
        try {
            repository.removeTopic(UUID.fromString(topicId));
            return new SuccessResponseDto("Topic with id: " + topicId + " was removed");
        }catch (RepositoryException ex){
            throw new ResponseStatusException(HttpStatus.CONFLICT,ex.getMessage());
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
