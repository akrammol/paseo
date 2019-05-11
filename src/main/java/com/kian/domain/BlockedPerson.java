package com.kian.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BlockedPerson.
 */
@Entity
@Table(name = "blocked_person")
public class BlockedPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("blockedPeople")
    private Person pesron;

    @ManyToOne
    @JsonIgnoreProperties("blockedPeople")
    private Person blockedPerson;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPesron() {
        return pesron;
    }

    public BlockedPerson pesron(Person person) {
        this.pesron = person;
        return this;
    }

    public void setPesron(Person person) {
        this.pesron = person;
    }

    public Person getBlockedPerson() {
        return blockedPerson;
    }

    public BlockedPerson blockedPerson(Person person) {
        this.blockedPerson = person;
        return this;
    }

    public void setBlockedPerson(Person person) {
        this.blockedPerson = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlockedPerson)) {
            return false;
        }
        return id != null && id.equals(((BlockedPerson) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BlockedPerson{" +
            "id=" + getId() +
            "}";
    }
}
