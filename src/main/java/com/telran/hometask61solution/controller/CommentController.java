package com.telran.hometask61solution.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.hometask61solution.controller.dto.*;
import com.telran.hometask61solution.repository.TopicRepository;
import com.telran.hometask61solution.repository.entity.CommentEntity;
import com.telran.hometask61solution.repository.exceptions.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class CommentController {
    @Autowired
    TopicRepository repository;
    @Autowired
    ObjectMapper mapper;

    @PostMapping("comment")
    public FullCommentDto addComment(@RequestBody AddCommentDto comment){
        FullCommentDto fullCommentDto = FullCommentDto.fullCommentBuilder()
                .id(UUID.randomUUID().toString())
                .author(comment.getAuthor())
                .date(LocalDateTime.now())
                .message(comment.getMessage())
                .build();
        try {
            repository.addComment(UUID.fromString(comment.getTopicId()), map(fullCommentDto));
            return fullCommentDto;
        }catch (RepositoryException ex){
            throw new ResponseStatusException(HttpStatus.CONFLICT,ex.getMessage());
        }
    }


    @DeleteMapping("comment")
    public SuccessResponseDto removeComment(@RequestBody RemoveCommentDto body){
        try {
            repository.removeComment(UUID.fromString(body.getTopicId()), UUID.fromString(body.getCommentId()));
            return new SuccessResponseDto("Comment with id: " + body.getCommentId() + " was removed");
        }catch (RepositoryException ex){
            throw new ResponseStatusException(HttpStatus.CONFLICT,ex.getMessage());
        }
    }

    @PutMapping("comment")
    public SuccessResponseDto updateComment(@RequestBody UpdateCommentDto body){
        try {
            repository.updateComment(UUID.fromString(body.getTopicId()), map(body));
            return new SuccessResponseDto("Comment with id: " + body.getId() + " was updated");
        }catch (RepositoryException ex){
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    private CommentEntity map(FullCommentDto comment) {
        return new CommentEntity(UUID.fromString(comment.getId()),
                comment.getAuthor(),
                comment.getMessage(),
                comment.getDate());
    }
}
