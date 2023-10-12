package com.emreilgar.service;

import com.emreilgar.repository.IDislikeRepository;
import com.emreilgar.repository.entity.Dislike;
import com.emreilgar.utillity.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class DislikeService extends ServiceManager<Dislike,String> {
    private final IDislikeRepository repository;

    public DislikeService(IDislikeRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
