package com.kian.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.kian.domain.enumeration.TagType;

/**
 * Tag entity.
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TagType type;

    @Column(name = "tag_value")
    private String tagValue;

    @ManyToOne
    @JsonIgnoreProperties("tags")
    private Comment comment;

    @ManyToOne
    @JsonIgnoreProperties("tags")
    private Post post;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TagType getType() {
        return type;
    }

    public Tag type(TagType type) {
        this.type = type;
        return this;
    }

    public void setType(TagType type) {
        this.type = type;
    }

    public String getTagValue() {
        return tagValue;
    }

    public Tag tagValue(String tagValue) {
        this.tagValue = tagValue;
        return this;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public Comment getComment() {
        return comment;
    }

    public Tag comment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public Tag post(Post post) {
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
        if (!(o instanceof Tag)) {
            return false;
        }
        return id != null && id.equals(((Tag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", tagValue='" + getTagValue() + "'" +
            "}";
    }
}
