package com.emreilgar.repository;


import com.emreilgar.repository.entity.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ILikeRepository extends MongoRepository<Like,String> {
}
