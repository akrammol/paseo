package com.kian.service.mapper;

import com.kian.domain.*;
import com.kian.service.dto.TagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommentMapper.class, PostMapper.class})
public interface TagMapper extends EntityMapper<TagDTO, Tag> {

    @Mapping(source = "comment.id", target = "commentId")
    @Mapping(source = "post.id", target = "postId")
    TagDTO toDto(Tag tag);

    @Mapping(source = "commentId", target = "comment")
    @Mapping(source = "postId", target = "post")
    Tag toEntity(TagDTO tagDTO);

    default Tag fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tag tag = new Tag();
        tag.setId(id);
        return tag;
    }
}
