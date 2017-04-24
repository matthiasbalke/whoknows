package com.github.reflectoring.whoknows.user

import org.hibernate.validator.constraints.Length

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "USERS")
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id

    @Length(min = 1, max = 255)
    @NotNull
    private String username

    @Length(min = 1, max = 500)
    @NotNull
    private String firstName

    @Length(min = 1, max = 500)
    @NotNull
    private String lastName

    @Length(min = 1, max = 254)
    @NotNull
    private String email

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getFirstName() {
        return firstName
    }

    void setFirstName(String firstName) {
        this.firstName = firstName
    }

    String getLastName() {
        return lastName
    }

    void setLastName(String lastName) {
        this.lastName = lastName
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }
}
