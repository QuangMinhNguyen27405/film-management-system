package com.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "address_id", unique = true)
    private Address address;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    public Store() {
        this.lastUpdate = LocalDateTime.now();
    }
}
