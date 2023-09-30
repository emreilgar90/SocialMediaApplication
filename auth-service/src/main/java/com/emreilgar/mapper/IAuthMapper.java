package com.emreilgar.mapper;

import com.emreilgar.dto.request.NewCreateUserDto;
import com.emreilgar.dto.request.RegisterRequestDto;
import com.emreilgar.dto.response.ActivateResponseDto;
import com.emreilgar.dto.response.LoginResponseDto;
import com.emreilgar.dto.response.RegisterResponseDto;
import com.emreilgar.dto.response.RoleResponseDto;
import com.emreilgar.rabbitmq.model.NewCreateUserModel;
import com.emreilgar.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface IAuthMapper {

    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth toAuth(final RegisterRequestDto dto); //dışarıdan aldığımız dto'yu Auth'a çevirdik.
    RegisterResponseDto toRegisterResponseDto(final Auth auth);
    ActivateResponseDto toActivateResponseDto(final Auth auth);


    @Mapping(source ="id" ,target ="authId")
    NewCreateUserDto toNewCreateUserDto(final Auth auth);
    List<ActivateResponseDto> toActivateResponseDto(final List<Auth> authList);
    LoginResponseDto toLoginResponseDto(final Auth auth);
    //Auth toAuth(final ActivateRequestDto dto);
   // Auth toAuth(final LoginRequestDto dto);

    List<RoleResponseDto> toRoleResponseDtos(final List<Auth> authList);

    @Mapping(source = "role",target = "role")
    RoleResponseDto toRoleResponseDto(final Auth auth);

    @Mapping(source = "id",target = "authId") //auth da ki id yi model de ki authId'ye eşlemiş olduk.
    NewCreateUserModel toNewCreateUserModel(final Auth auth);




}

