package com.emreilgar.mapper;

import com.emreilgar.dto.request.NewCreateUserDto;
import com.emreilgar.dto.request.UpdateRequestDto;
import com.emreilgar.dto.response.UpdateResponseDto;
import com.emreilgar.rabbitmq.model.NewCreateUserModel;
import com.emreilgar.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE= Mappers.getMapper(IUserMapper.class);

    UserProfile toUserProfile(final NewCreateUserDto dto); //dto dan UserProfile'e çevir.


    UserProfile toUserProfile(final UpdateRequestDto dto);

    UpdateResponseDto toUpdateResponseDto(final UpdateRequestDto dto);
    UpdateResponseDto toUpdateResponseDto(final UserProfile userProfile);

    UserProfile toUserProfile( final NewCreateUserModel model);

    //UserProfile toUserProfile(final ActivateRequestDto dto);




}
