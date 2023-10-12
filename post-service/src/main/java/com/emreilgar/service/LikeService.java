package com.emreilgar.service;

import com.emreilgar.repository.ILikeRepository;
import com.emreilgar.repository.entity.Like;
import com.emreilgar.utillity.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class LikeService extends ServiceManager<Like,String> {
    private final ILikeRepository repository;
    public LikeService(ILikeRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
