package org.mizuro.aviatickets.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@JsonIgnoreProperties("passports")
@Table(name = "_countries")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    private int id;

    @Size(max = 60)
    @Column(name = "title_ru", length = 60)
    private String titleRu;

    @Size(max = 60)
    @Column(name = "title_ua", length = 60)
    private String titleUa;

    @Size(max = 60)
    @Column(name = "title_be", length = 60)
    private String titleBe;

    @Size(max = 60)
    @Column(name = "title_en", length = 60)
    private String titleEn;

    @Size(max = 60)
    @Column(name = "title_es", length = 60)
    private String titleEs;

    @Size(max = 60)
    @Column(name = "title_pt", length = 60)
    private String titlePt;

    @Size(max = 60)
    @Column(name = "title_de", length = 60)
    private String titleDe;

    @Size(max = 60)
    @Column(name = "title_fr", length = 60)
    private String titleFr;

    @Size(max = 60)
    @Column(name = "title_it", length = 60)
    private String titleIt;

    @Size(max = 60)
    @Column(name = "title_pl", length = 60)
    private String titlePl;

    @Size(max = 60)
    @Column(name = "title_ja", length = 60)
    private String titleJa;

    @Size(max = 60)
    @Column(name = "title_lt", length = 60)
    private String titleLt;

    @Size(max = 60)
    @Column(name = "title_lv", length = 60)
    private String titleLv;

    @Size(max = 60)
    @Column(name = "title_cz", length = 60)
    private String titleCz;

    @OneToMany(mappedBy = "nationality")
    private Set<PassportEntity> passports = new LinkedHashSet<>();

}