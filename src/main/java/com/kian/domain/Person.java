package com.kian.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Person entity.
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The userName attribute.
     */
    @Column(name = "bio")
    private String bio;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_pic_url")
    private String profilePicURL;

    @NotNull
    @Column(name = "party_id", nullable = false)
    private Long partyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public Person bio(String bio) {
        this.bio = bio;
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUserName() {
        return userName;
    }

    public Person userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Person displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public Person email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public Person profilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
        return this;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public Long getPartyId() {
        return partyId;
    }

    public Person partyId(Long partyId) {
        this.partyId = partyId;
        return this;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", bio='" + getBio() + "'" +
            ", userName='" + getUserName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", email='" + getEmail() + "'" +
            ", profilePicURL='" + getProfilePicURL() + "'" +
            ", partyId=" + getPartyId() +
            "}";
    }
}
