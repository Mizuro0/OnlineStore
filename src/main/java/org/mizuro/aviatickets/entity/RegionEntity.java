package org.mizuro.aviatickets.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "_regions")
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "country_id", nullable = false)
    private Integer countryId;

    @Size(max = 150)
    @Column(name = "title_ru", length = 150)
    private String titleRu;

    @Size(max = 150)
    @Column(name = "title_ua", length = 150)
    private String titleUa;

    @Size(max = 150)
    @Column(name = "title_be", length = 150)
    private String titleBe;

    @Size(max = 150)
    @Column(name = "title_en", length = 150)
    private String titleEn;

    @Size(max = 150)
    @Column(name = "title_es", length = 150)
    private String titleEs;

    @Size(max = 150)
    @Column(name = "title_pt", length = 150)
    private String titlePt;

    @Size(max = 150)
    @Column(name = "title_de", length = 150)
    private String titleDe;

    @Size(max = 150)
    @Column(name = "title_fr", length = 150)
    private String titleFr;

    @Size(max = 150)
    @Column(name = "title_it", length = 150)
    private String titleIt;

    @Size(max = 150)
    @Column(name = "title_pl", length = 150)
    private String titlePl;

    @Size(max = 150)
    @Column(name = "title_ja", length = 150)
    private String titleJa;

    @Size(max = 150)
    @Column(name = "title_lt", length = 150)
    private String titleLt;

    @Size(max = 150)
    @Column(name = "title_lv", length = 150)
    private String titleLv;

    @Size(max = 150)
    @Column(name = "title_cz", length = 150)
    private String titleCz;
}