package com.emreilgar.repository;


import com.emreilgar.repository.entity.Dislike;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IDislikeRepository extends MongoRepository<Dislike,String> {
}
