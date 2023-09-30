package com.emreilgar.repository;

import com.emreilgar.repository.entity.UserProfile;

import com.emreilgar.repository.enums.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserProfileRepository extends MongoRepository<UserProfile,String> {

    Optional<UserProfile> findOptionalByAuthId(Long authId);


    /*@Query("{$and: [{status:?0},{name:?1}]}") //MONGODB'nin query'lerine bak !
    List<UserProfile> findAllActiveProfile(String value,String name);    13.01->2:37*/


    @Query("{status: 'ACTIVE'}") //MONGODB'nin query'lerine bak !
    List<UserProfile> findAllActiveProfile();


    Optional<UserProfile> findOptionalByUsername(String username);
}
