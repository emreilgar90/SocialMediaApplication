package com.emreilgar.service;

import com.emreilgar.dto.request.PostCreateRequestDto;
import com.emreilgar.dto.response.PostCreateResponseDto;
import com.emreilgar.mapper.IPostMapper;
import com.emreilgar.repository.IPostRepository;
import com.emreilgar.repository.entity.Post;
import com.emreilgar.utillity.ServiceManager;
import org.springframework.stereotype.Service;
@Service
public class PostService extends ServiceManager<Post,String> {
    private final IPostRepository repository;
    public PostService(IPostRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public PostCreateResponseDto createPost(PostCreateRequestDto dto) {
       Post post= IPostMapper.INSTANCE.toPost(dto);
        save(post);
        return IPostMapper.INSTANCE.toPostCreateResponseDto(post);
    }
}
