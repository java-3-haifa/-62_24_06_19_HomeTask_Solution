package com.telran.hometask61solution.repository;

import com.telran.hometask61solution.repository.entity.CommentEntity;
import com.telran.hometask61solution.repository.entity.TopicEntity;
import com.telran.hometask61solution.repository.exceptions.RepositoryException;

import java.util.UUID;

public interface TopicRepository {
    boolean addTopic(TopicEntity topic);
    boolean removeTopic(UUID id);
    Iterable<TopicEntity> getAllTopics();

    boolean addComment(UUID topicId, CommentEntity comment);
    boolean removeComment(UUID topicId, UUID commentId);

    default boolean updateComment(UUID topicId, CommentEntity comment){
        if(removeComment(topicId,comment.getId())){
            return addComment(topicId,comment);
        }
        throw new RepositoryException("Repository error");
    }
}
