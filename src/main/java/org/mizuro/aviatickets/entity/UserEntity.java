package org.mizuro.aviatickets.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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
    @Column(name = "id")
    private int id;

    @NotNull
    @Size(min = 6)
    @Column(name = "username")
    private String username;

    @NotNull
    @Size(min = 8)
    @Column(name = "password")
    private String password;

    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "role")
    private Role role = Role.ROLE_USER;

    @Column(name = "nickname")
    @NotNull
    private String nickname;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "non_locked")
    private boolean nonLocked = true;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<TicketEntity> tickets;

    @OneToOne
    @JoinColumn(name = "passport_id", referencedColumnName = "id", unique = true)
    private PassportEntity passport;

}
