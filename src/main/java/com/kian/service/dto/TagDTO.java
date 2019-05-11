package com.kian.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import com.kian.domain.enumeration.TagType;

/**
 * A DTO for the {@link com.kian.domain.Tag} entity.
 */
@ApiModel(description = "Tag entity.")
public class TagDTO implements Serializable {

    private Long id;

    private TagType value;


    private Long personId;

    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TagType getValue() {
        return value;
    }

    public void setValue(TagType value) {
        this.value = value;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TagDTO tagDTO = (TagDTO) o;
        if (tagDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tagDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TagDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", person=" + getPersonId() +
            ", post=" + getPostId() +
            "}";
    }
}
