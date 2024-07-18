package com.crm.repository;

import com.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> { //<class.name, class_id.type>

    @Query("from Customer c where c.email = ?1 and c.customerId <> ?2")
    Customer findByEmaiExcludeId(String email, Long customerId);

    Optional<Customer> findByEmail(String email);

    @Query("from Customer c where c.email = ?1 and c.active = true")
    Optional<Customer> findByEmailActive(String email);
}
