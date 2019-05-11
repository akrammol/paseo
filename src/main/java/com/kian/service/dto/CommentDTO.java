package com.kian.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.kian.domain.enumeration.CommentStatus;

/**
 * A DTO for the {@link com.kian.domain.Comment} entity.
 */
public class CommentDTO implements Serializable {

    private Long id;

    @NotNull
    private String text;

    private Long commentCount;

    private Long likeCount;

    @NotNull
    private CommentStatus status;


    private Long replyToId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public Long getReplyToId() {
        return replyToId;
    }

    public void setReplyToId(Long commentId) {
        this.replyToId = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentDTO commentDTO = (CommentDTO) o;
        if (commentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", commentCount=" + getCommentCount() +
            ", likeCount=" + getLikeCount() +
            ", status='" + getStatus() + "'" +
            ", replyTo=" + getReplyToId() +
            "}";
    }
}
