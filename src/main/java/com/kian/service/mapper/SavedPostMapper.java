package com.kian.service.mapper;

import com.kian.domain.*;
import com.kian.service.dto.SavedPostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SavedPost} and its DTO {@link SavedPostDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, PostMapper.class})
public interface SavedPostMapper extends EntityMapper<SavedPostDTO, SavedPost> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "post.id", target = "postId")
    SavedPostDTO toDto(SavedPost savedPost);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "postId", target = "post")
    SavedPost toEntity(SavedPostDTO savedPostDTO);

    default SavedPost fromId(Long id) {
        if (id == null) {
            return null;
        }
        SavedPost savedPost = new SavedPost();
        savedPost.setId(id);
        return savedPost;
    }
}
