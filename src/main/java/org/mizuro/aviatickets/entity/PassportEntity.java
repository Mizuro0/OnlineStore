package org.mizuro.aviatickets.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "passports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PassportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "number")
    private String number;

    @NotNull
    @Column(name = "serial")
    private String serial;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "surname")
    @NotNull
    private String surname;

    @Column(name = "nationality")
    @NotNull
    private String nationality;

    @Column(name = "issue_date")
    @NotNull
    private String issueDate;

    @Column(name = "expiration_date")
    @NotNull
    private String expirationDate;

    @Column(name = "date_of_birth")
    @NotNull
    private String birthDate;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", unique = true)
    private UserEntity owner;
}
