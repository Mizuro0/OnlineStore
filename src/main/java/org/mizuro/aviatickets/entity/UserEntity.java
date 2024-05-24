package org.mizuro.aviatickets.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "persons")
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Size(max = 255)
    @NotNull(message = "Username cannot be empty")
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 255)
    @NotNull(message = "Password cannot be empty")
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 255)
    @NotNull(message = "Email cannot be empty")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "role")
    private Role role = Role.ROLE_USER;

    @NotNull
    @Column(name = "active", nullable = false)
    private boolean active = true;

    @NotNull
    @Column(name = "non_locked", nullable = false)
    private boolean nonLocked = true;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<TicketEntity> tickets;

    @NotNull(message = "Nickname cannot be empty")
    @Size(max = 255)
    @Column(name = "nickname")
    private String nickname;

    @OneToOne(mappedBy = "person")
    private PassportEntity passport;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", active=" + active +
                ", nonLocked=" + nonLocked +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
