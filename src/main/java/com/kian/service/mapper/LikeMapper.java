package com.kian.service.mapper;

import com.kian.domain.*;
import com.kian.service.dto.LikeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Like} and its DTO {@link LikeDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, PostMapper.class, CommentMapper.class})
public interface LikeMapper extends EntityMapper<LikeDTO, Like> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "comment.id", target = "commentId")
    LikeDTO toDto(Like like);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "postId", target = "post")
    @Mapping(source = "commentId", target = "comment")
    Like toEntity(LikeDTO likeDTO);

    default Like fromId(Long id) {
        if (id == null) {
            return null;
        }
        Like like = new Like();
        like.setId(id);
        return like;
    }
}
