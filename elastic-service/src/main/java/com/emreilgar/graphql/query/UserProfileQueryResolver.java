package com.emreilgar.graphql.query;

import com.emreilgar.repository.entity.UserProfile;
import com.emreilgar.service.UserProfileService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserProfileQueryResolver implements GraphQLQueryResolver {

    private final UserProfileService userProfileService;



    public Iterable<UserProfile>  findAll(){

        return userProfileService.findAll();
    }

    public List<UserProfile> findAllContainingEmail(String value){

        return  userProfileService.findAllContainingEmail(value);
    }

    public List<UserProfile> findAllByStatus(String status){

        return  userProfileService.findAllByStatus(status);
    }

    public List<UserProfile> findAllByStatusOrAddress(String status,String address){

        return  userProfileService.findAllByStatusOrAddress(status,address);
    }

    public  UserProfile findByUsername(String username){

        return  userProfileService.findByUsername(username);
    }

}
