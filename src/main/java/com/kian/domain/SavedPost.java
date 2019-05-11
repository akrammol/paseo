package com.kian.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SavedPost.
 */
@Entity
@Table(name = "saved_post")
public class SavedPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "save_time")
    private Instant saveTime;

    @ManyToOne
    @JsonIgnoreProperties("savedPosts")
    private Person person;

    @ManyToOne
    @JsonIgnoreProperties("savedPosts")
    private Post post;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSaveTime() {
        return saveTime;
    }

    public SavedPost saveTime(Instant saveTime) {
        this.saveTime = saveTime;
        return this;
    }

    public void setSaveTime(Instant saveTime) {
        this.saveTime = saveTime;
    }

    public Person getPerson() {
        return person;
    }

    public SavedPost person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Post getPost() {
        return post;
    }

    public SavedPost post(Post post) {
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
        if (!(o instanceof SavedPost)) {
            return false;
        }
        return id != null && id.equals(((SavedPost) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SavedPost{" +
            "id=" + getId() +
            ", saveTime='" + getSaveTime() + "'" +
            "}";
    }
}
