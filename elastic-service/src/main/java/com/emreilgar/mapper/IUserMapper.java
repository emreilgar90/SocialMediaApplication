package com.emreilgar.mapper;

import com.emreilgar.dto.request.NewCreateUserDto;
import com.emreilgar.dto.request.UpdateRequestDto;
import com.emreilgar.dto.response.UpdateResponseDto;
import com.emreilgar.graphql.model.UserProfileInput;
import com.emreilgar.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IUserMapper {


    IUserMapper INSTANCE= Mappers.getMapper(IUserMapper.class);


    UserProfile toUserProfile(final NewCreateUserDto dto);


    UserProfile toUserProfile(final UpdateRequestDto dto);
    UpdateResponseDto toUpdateResponseDto(final UpdateRequestDto dto);
    UpdateResponseDto toUpdateResponseDto(final UserProfile userProfile);

    UserProfile toUserProfile(final UserProfileInput profile);

}