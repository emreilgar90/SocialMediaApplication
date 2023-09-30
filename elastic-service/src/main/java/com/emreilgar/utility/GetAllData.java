package com.emreilgar.utility;

import com.emreilgar.manager.IUserManager;
import com.emreilgar.repository.entity.UserProfile;
import com.emreilgar.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllData {

    private final UserProfileService userProfileService;
    private final IUserManager userManager;

    // @PostConstruct
    public void initData(){
        List<UserProfile> userProfiles=userManager.findAll().getBody();
        userProfileService.saveAll(userProfiles);
    }

}
