package com.incidenciasvlc.mysqlservice.repository;

import com.incidenciasvlc.mysqlservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
