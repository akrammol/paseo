package com.kian.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.kian.domain.enumeration.PostStatus;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
public class Post implements Serializable {

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
    private PostStatus status;

    @ManyToOne
    @JsonIgnoreProperties("posts")
    private Person person;

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

    public Post text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public Post commentCount(Long commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public Post likeCount(Long likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public PostStatus getStatus() {
        return status;
    }

    public Post status(PostStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public Post person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", commentCount=" + getCommentCount() +
            ", likeCount=" + getLikeCount() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
