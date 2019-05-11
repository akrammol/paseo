package com.kian.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kian.domain.SavedPost} entity.
 */
public class SavedPostDTO implements Serializable {

    private Long id;

    private Instant saveTime;


    private Long personId;

    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Instant saveTime) {
        this.saveTime = saveTime;
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

        SavedPostDTO savedPostDTO = (SavedPostDTO) o;
        if (savedPostDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), savedPostDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SavedPostDTO{" +
            "id=" + getId() +
            ", saveTime='" + getSaveTime() + "'" +
            ", person=" + getPersonId() +
            ", post=" + getPostId() +
            "}";
    }
}
