package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", length = 25, unique = true)
    private String name;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private Set<Film> films = new HashSet<>();

    public Category() {
        this.lastUpdate = LocalDateTime.now();
    }
}
