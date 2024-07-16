package com.crm.repository;

import com.crm.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("from Store s join s.address a where a.address = ?1")
    Optional<Store> findByAddress(String address);
}
