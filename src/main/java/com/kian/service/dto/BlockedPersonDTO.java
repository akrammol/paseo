package com.kian.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kian.domain.BlockedPerson} entity.
 */
public class BlockedPersonDTO implements Serializable {

    private Long id;


    private Long pesronId;

    private Long blockedPersonId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPesronId() {
        return pesronId;
    }

    public void setPesronId(Long personId) {
        this.pesronId = personId;
    }

    public Long getBlockedPersonId() {
        return blockedPersonId;
    }

    public void setBlockedPersonId(Long personId) {
        this.blockedPersonId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlockedPersonDTO blockedPersonDTO = (BlockedPersonDTO) o;
        if (blockedPersonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blockedPersonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BlockedPersonDTO{" +
            "id=" + getId() +
            ", pesron=" + getPesronId() +
            ", blockedPerson=" + getBlockedPersonId() +
            "}";
    }
}
