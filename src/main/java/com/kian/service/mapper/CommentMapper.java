package com.kian.service.mapper;

import com.kian.domain.*;
import com.kian.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "replyTo.id", target = "replyToId")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "replyToId", target = "replyTo")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
