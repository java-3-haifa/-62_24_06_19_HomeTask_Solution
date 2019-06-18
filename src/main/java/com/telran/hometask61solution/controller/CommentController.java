package com.telran.hometask61solution.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.hometask61solution.controller.dto.*;
import com.telran.hometask61solution.repository.TopicRepository;
import com.telran.hometask61solution.repository.entity.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class CommentController {
    @Autowired
    TopicRepository repository;
    @Autowired
    ObjectMapper mapper;

    @PostMapping("comment")
    public ResponseEntity<String> addComment(@RequestBody AddCommentDto comment){
        FullCommentDto fullCommentDto = FullCommentDto.fullCommentBuilder()
                .id(UUID.randomUUID().toString())
                .author(comment.getAuthor())
                .date(LocalDateTime.now())
                .message(comment.getMessage())
                .build();
        repository.addComment(UUID.fromString(comment.getTopicId()),map(fullCommentDto));

        try {
            return ResponseEntity.ok(mapper.writeValueAsString(fullCommentDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Parse exception");
        }
    }


    @DeleteMapping("comment")
    public ResponseEntity<String> removeComment(@RequestBody RemoveCommentDto body){
        repository.removeComment(UUID.fromString(body.getTopicId()),UUID.fromString(body.getCommentId()));
        SuccessResponseDto response = new SuccessResponseDto("Comment with id:" + body.getCommentId() + " was removed");
        try {
            return ResponseEntity.ok(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Parse error");
        }
    }

    @PutMapping("comment")
    public ResponseEntity<String> updateComment(@RequestBody UpdateCommentDto body){
        repository.updateComment(UUID.fromString(body.getTopicId()),map(body));
        SuccessResponseDto response = new SuccessResponseDto("Comment with id: " + body.getId() + " was updated");
        try {
            return ResponseEntity.ok(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Parse error");
        }
    }

    private CommentEntity map(FullCommentDto comment) {
        return new CommentEntity(UUID.fromString(comment.getId()),
                comment.getAuthor(),
                comment.getMessage(),
                comment.getDate());
    }
}
