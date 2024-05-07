package org.mizuro.aviatickets.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "_cities")
@ToString
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "country_id", nullable = false)
    private Integer countryId;


    @NotNull
    @Column(name = "important", nullable = false)
    private Boolean important = false;

    @Column(name = "region_id")
    private Integer regionId;

    @Size(max = 150)
    @Column(name = "title_ru", length = 150)
    private String titleRu;

    @Size(max = 150)
    @Column(name = "area_ru", length = 150)
    private String areaRu;

    @Size(max = 150)
    @Column(name = "region_ru", length = 150)
    private String regionRu;

    @Size(max = 150)
    @Column(name = "title_ua", length = 150)
    private String titleUa;

    @Size(max = 150)
    @Column(name = "area_ua", length = 150)
    private String areaUa;

    @Size(max = 150)
    @Column(name = "region_ua", length = 150)
    private String regionUa;

    @Size(max = 150)
    @Column(name = "title_be", length = 150)
    private String titleBe;

    @Size(max = 150)
    @Column(name = "area_be", length = 150)
    private String areaBe;

    @Size(max = 150)
    @Column(name = "region_be", length = 150)
    private String regionBe;

    @Size(max = 150)
    @Column(name = "title_en", length = 150)
    private String titleEn;

    @Size(max = 150)
    @Column(name = "area_en", length = 150)
    private String areaEn;

    @Size(max = 150)
    @Column(name = "region_en", length = 150)
    private String regionEn;

    @Size(max = 150)
    @Column(name = "title_es", length = 150)
    private String titleEs;

    @Size(max = 150)
    @Column(name = "area_es", length = 150)
    private String areaEs;

    @Size(max = 150)
    @Column(name = "region_es", length = 150)
    private String regionEs;

    @Size(max = 150)
    @Column(name = "title_pt", length = 150)
    private String titlePt;

    @Size(max = 150)
    @Column(name = "area_pt", length = 150)
    private String areaPt;

    @Size(max = 150)
    @Column(name = "region_pt", length = 150)
    private String regionPt;

    @Size(max = 150)
    @Column(name = "title_de", length = 150)
    private String titleDe;

    @Size(max = 150)
    @Column(name = "area_de", length = 150)
    private String areaDe;

    @Size(max = 150)
    @Column(name = "region_de", length = 150)
    private String regionDe;

    @Size(max = 150)
    @Column(name = "title_fr", length = 150)
    private String titleFr;

    @Size(max = 150)
    @Column(name = "area_fr", length = 150)
    private String areaFr;

    @Size(max = 150)
    @Column(name = "region_fr", length = 150)
    private String regionFr;

    @Size(max = 150)
    @Column(name = "title_it", length = 150)
    private String titleIt;

    @Size(max = 150)
    @Column(name = "area_it", length = 150)
    private String areaIt;

    @Size(max = 150)
    @Column(name = "region_it", length = 150)
    private String regionIt;

    @Size(max = 150)
    @Column(name = "title_pl", length = 150)
    private String titlePl;

    @Size(max = 150)
    @Column(name = "area_pl", length = 150)
    private String areaPl;

    @Size(max = 150)
    @Column(name = "region_pl", length = 150)
    private String regionPl;

    @Size(max = 150)
    @Column(name = "title_ja", length = 150)
    private String titleJa;

    @Size(max = 150)
    @Column(name = "area_ja", length = 150)
    private String areaJa;

    @Size(max = 150)
    @Column(name = "region_ja", length = 150)
    private String regionJa;

    @Size(max = 150)
    @Column(name = "title_lt", length = 150)
    private String titleLt;

    @Size(max = 150)
    @Column(name = "area_lt", length = 150)
    private String areaLt;

    @Size(max = 150)
    @Column(name = "region_lt", length = 150)
    private String regionLt;

    @Size(max = 150)
    @Column(name = "title_lv", length = 150)
    private String titleLv;

    @Size(max = 150)
    @Column(name = "area_lv", length = 150)
    private String areaLv;

    @Size(max = 150)
    @Column(name = "region_lv", length = 150)
    private String regionLv;

    @Size(max = 150)
    @Column(name = "title_cz", length = 150)
    private String titleCz;

    @Size(max = 150)
    @Column(name = "area_cz", length = 150)
    private String areaCz;

    @Size(max = 150)
    @Column(name = "region_cz", length = 150)
    private String regionCz;
}