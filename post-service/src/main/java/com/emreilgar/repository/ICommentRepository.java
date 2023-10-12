package com.emreilgar.repository;

import com.emreilgar.repository.entity.Comment;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICommentRepository extends MongoRepository<Comment,String> {
}
