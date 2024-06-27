package com.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Long filmId;

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "language_id")
    private Language language;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "rental_duration")
    private Long rentalDuration;

    @Column(name = "rental_rate")
    private BigInteger rentalRate;

    @Column(name = "length")
    private Integer length;

    @Column(name = "replacement_cost")
    private BigInteger replacementCost;

    @Column(name = "rating")
    private Long rating;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "special_features")
    private String specialFeatures;

    @Column(name = "full_text")
    private String fullText;

    @Column(name = "picture_url")
    private String pictureURL;

    @ManyToMany
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private Set<Inventory> inventories = new HashSet<>();

    public Film() {
        this.lastUpdate = LocalDateTime.now();
    }

    public Film(Film film) {
        this.language = film.getLanguage();
        this.title = film.getTitle();
        this.description = film.getDescription();
        this.releaseYear = film.getReleaseYear();
        this.rentalDuration = film.getRentalDuration();
        this.length = film.getLength();
        this.rating = film.getRating();
        this.specialFeatures = film.getSpecialFeatures();
        this.fullText = film.getFullText();
        this.lastUpdate = LocalDateTime.now();
    }
}
