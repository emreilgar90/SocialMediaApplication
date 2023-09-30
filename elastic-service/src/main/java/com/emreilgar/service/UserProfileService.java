package com.emreilgar.service;

import com.emreilgar.exception.ElasticManagerException;
import com.emreilgar.exception.ErrorType;
import com.emreilgar.repository.IUserProfileRepository;
import com.emreilgar.repository.entity.UserProfile;
import com.emreilgar.repository.enums.Status;
import com.emreilgar.utility.ServiceManager;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Locale;


@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private  final IUserProfileRepository userProfileRepository;


    public UserProfileService(IUserProfileRepository userProfileRepository) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;

    }


    public List<UserProfile> findAllContainingEmail(String value) {

        return  userProfileRepository.findAllByEmailContainingIgnoreCase(value);
    }

    public List<UserProfile> findAllByStatus(String status) {

        return  userProfileRepository.findAllByStatus(Status.valueOf(status.toUpperCase(Locale.ENGLISH)));

    }

    public List<UserProfile> findAllByStatusOrAddress(String status, String address) {

        return  userProfileRepository.
                findAllByStatusOrAddress(Status.valueOf(status.toUpperCase(Locale.ENGLISH)),address);
    }

    public  UserProfile findByUsername(String username){
        UserProfile userProfile;
        try {
            userProfile =userProfileRepository.findOptionalByUsernameEqualsIgnoreCase(username).get();
        }catch (Exception e){
            throw  new ElasticManagerException(ErrorType.USER_NOT_FOUND);
        }
        return  userProfile;
    }



}

