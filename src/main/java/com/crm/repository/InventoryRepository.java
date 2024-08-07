package com.crm.repository;

import com.crm.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("from Inventory i join i.film f where f.filmId = ?1")
    List<Inventory> findByFilmId(Long filmId);
}
