package com.kian.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kian.domain.Like} entity.
 */
public class LikeDTO implements Serializable {

    private Long id;

    private Instant submitTime;


    private Long personId;

    private Long postId;

    private Long commentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Instant submitTime) {
        this.submitTime = submitTime;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LikeDTO likeDTO = (LikeDTO) o;
        if (likeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), likeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LikeDTO{" +
            "id=" + getId() +
            ", submitTime='" + getSubmitTime() + "'" +
            ", person=" + getPersonId() +
            ", post=" + getPostId() +
            ", comment=" + getCommentId() +
            "}";
    }
}
