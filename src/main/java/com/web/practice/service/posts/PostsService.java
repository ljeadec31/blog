package com.web.practice.service.posts;

import com.web.practice.domain.posts.Posts;
import com.web.practice.domain.posts.PostsRepository;
import com.web.practice.web.dto.PostsListResponseDto;
import com.web.practice.web.dto.PostsResponseDto;
import com.web.practice.web.dto.PostsSaveRequestDto;
import com.web.practice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto){
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no such posts. id="+id));
        posts.update(requestDto.getTitle(),requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no such posts. id="+id));
        postsRepository.delete(posts);
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no such posts. id="+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesec().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
