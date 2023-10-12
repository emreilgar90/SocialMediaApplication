package com.emreilgar.mapper;

import com.emreilgar.dto.request.PostCreateRequestDto;
import com.emreilgar.dto.response.PostCreateResponseDto;
import com.emreilgar.repository.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

    @Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface IPostMapper {

        IPostMapper INSTANCE= Mappers.getMapper(IPostMapper.class);

        PostCreateResponseDto toPostCreateResponseDto(final Post post);

        Post toPost(final PostCreateRequestDto dto);




}
