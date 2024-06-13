package com.crm.repository;

import com.crm.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

//    @Query("select * " +
//            "from film f join film_category fc on f.film_id = fc.film_id " +
//            "join category c on c.category_id = fc.category_id")
//    List<Film> findAll();
//
//    @Query("select * " +
//            "from film f join film_category fc on f.film_id = fc.film_id " +
//            "join category c on c.category_id = fc.category_id" +
//            "where c.category_name = :category_name")
//    List<Film> findByParam(@Param("category_name") String categoryName);


}
