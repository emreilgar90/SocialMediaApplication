package com.emreilgar.service;

import com.emreilgar.repository.ICommentRepository;
import com.emreilgar.repository.entity.Comment;
import com.emreilgar.utillity.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends ServiceManager<Comment,String> {

    private final ICommentRepository repository;

    public CommentService(ICommentRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
