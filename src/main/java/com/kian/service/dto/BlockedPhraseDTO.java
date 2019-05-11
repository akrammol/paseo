package com.kian.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kian.domain.BlockedPhrase} entity.
 */
public class BlockedPhraseDTO implements Serializable {

    private Long id;

    private String value;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlockedPhraseDTO blockedPhraseDTO = (BlockedPhraseDTO) o;
        if (blockedPhraseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blockedPhraseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BlockedPhraseDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
