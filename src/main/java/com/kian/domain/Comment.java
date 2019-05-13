package com.kian.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.kian.domain.enumeration.CommentStatus;

/**
 * A Comment.
 */
@Entity
@Table(name = "jhi_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "comment_count")
    private Long commentCount;

    @Column(name = "like_count")
    private Long likeCount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CommentStatus status;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Comment replyTo;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Post post;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Comment text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public Comment commentCount(Long commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public Comment likeCount(Long likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public Comment status(CommentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public Comment getReplyTo() {
        return replyTo;
    }

    public Comment replyTo(Comment comment) {
        this.replyTo = comment;
        return this;
    }

    public void setReplyTo(Comment comment) {
        this.replyTo = comment;
    }

    public Post getPost() {
        return post;
    }

    public Comment post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", commentCount=" + getCommentCount() +
            ", likeCount=" + getLikeCount() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
