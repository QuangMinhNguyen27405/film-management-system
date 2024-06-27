package com.crm.entity;

import com.crm.utils.DateUtils;
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
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "first_name", columnDefinition = "VARCHAR(50)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(50)")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "created_dtm")
    private LocalDateTime createdDtm;

    @Column(name = "email", unique = true, length = 50)
    private String email;

    @Column(name = "active")
    private boolean active;

    @ManyToMany
    @JoinTable(
            name="customers_roles",
            joinColumns={@JoinColumn(name="customer_id", referencedColumnName="customer_id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="role_id")})
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Rental> rentals = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    public Customer() {
        this.createdDtm = LocalDateTime.now();
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.active = true;
        this.createdDtm = DateUtils.getLocalDateTime();
    }
}
