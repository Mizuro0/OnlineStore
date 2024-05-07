package org.mizuro.aviatickets.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Column(name = "id", nullable = false)
    private int id;

    @Size(max = 255)
    @NotNull
    @Column(name = "number", nullable = false)
    private String number;

    @Size(max = 255)
    @NotNull
    @Column(name = "serial", nullable = false)
    private String serial;

    @NotNull
    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @Size(max = 255)
    @NotNull
    @Column(name = "issue_date", nullable = false)
    private String issueDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;

    @Size(max = 255)
    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nationality")
    private CountryEntity nationality;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "birth_place")
    private CityEntity birthPlace;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private UserEntity person;

    @Override
    public String toString() {
        return "PassportEntity{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", serial='" + serial + '\'' +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", nationality=" + nationality +
                ", birthPlace=" + birthPlace +
                ", person=" + person +
                '}';
    }
}
