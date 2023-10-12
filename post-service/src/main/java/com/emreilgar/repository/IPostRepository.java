package com.emreilgar.repository;

import com.emreilgar.repository.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IPostRepository extends MongoRepository<Post,String> {
}
