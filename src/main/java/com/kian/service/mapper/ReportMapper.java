package com.kian.service.mapper;

import com.kian.domain.*;
import com.kian.service.dto.ReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Report} and its DTO {@link ReportDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, PostMapper.class, CommentMapper.class})
public interface ReportMapper extends EntityMapper<ReportDTO, Report> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "comment.id", target = "commentId")
    ReportDTO toDto(Report report);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "postId", target = "post")
    @Mapping(source = "commentId", target = "comment")
    Report toEntity(ReportDTO reportDTO);

    default Report fromId(Long id) {
        if (id == null) {
            return null;
        }
        Report report = new Report();
        report.setId(id);
        return report;
    }
}
