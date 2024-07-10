package com.crm.repository;

import com.crm.entity.Customer;
import com.crm.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    @Query("from Staff s where s.email = ?1 and s.id <> ?2")
    Staff findByEmaiExcludeId(String email, Long staffId);
}
