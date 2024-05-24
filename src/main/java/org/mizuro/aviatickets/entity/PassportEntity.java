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

    @Size(min = 4, max = 4, message = "Number must be 4 digits long")
    @NotNull(message = "Number cannot be empty")
    @Column(name = "number", nullable = false)
    private String number;

    @Size(min = 6, max = 6, message = "Serial must be 6 digits long")
    @NotNull(message = "Serial cannot be empty")
    @Column(name = "serial", nullable = false)
    private String serial;

    @NotNull
    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Size(max = 255)
    @NotNull(message = "Name cannot be empty")
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull(message = "Surname cannot be empty")
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull(message = "Issue date cannot be empty")
    @Column(name = "issue_date", nullable = false)
    private String issueDate;

    @NotNull(message = "Expiration date cannot be empty")
    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;

    @Column(name = "date_of_birth")
    @NotNull(message = "Date of birth cannot be empty")
    private String dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nationality")
    @NotNull(message = "Nationality cannot be empty, please specify nationality")
    private CountryEntity nationality;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "birth_place")
    @NotNull(message = "Birth place cannot be empty, please specify birth place")
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
