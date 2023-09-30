package com.emreilgar.graphql.mutation;

import com.emreilgar.exception.ElasticManagerException;
import com.emreilgar.exception.ErrorType;
import com.emreilgar.graphql.model.UserProfileInput;
import com.emreilgar.mapper.IUserMapper;
import com.emreilgar.service.UserProfileService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileMutationResolver implements GraphQLMutationResolver {
    //endpointleri olmayan controller gibi düşünebiliriz

    private final UserProfileService userProfileService;

    public  Boolean createUserProfile(UserProfileInput profile){//17.01->1:50
        try {
            userProfileService.save(IUserMapper.INSTANCE.toUserProfile(profile));
            return true;
        }catch (Exception e){
            throw  new ElasticManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

}
