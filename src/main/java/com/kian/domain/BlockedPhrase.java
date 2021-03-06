package com.kian.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BlockedPhrase.
 */
@Entity
@Table(name = "blocked_phrase")
public class BlockedPhrase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_value")
    private String value;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public BlockedPhrase value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlockedPhrase)) {
            return false;
        }
        return id != null && id.equals(((BlockedPhrase) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BlockedPhrase{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
