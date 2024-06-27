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
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "address")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private Set<Customer> customer = new HashSet<>();

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    public Address() {
        this.lastUpdate = LocalDateTime.now();
    }

    public Address(String address) {
        this.lastUpdate = LocalDateTime.now();
        this.address = address;
    }
}
