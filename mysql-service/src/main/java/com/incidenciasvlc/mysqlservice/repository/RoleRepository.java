package com.incidenciasvlc.mysqlservice.repository;

import com.incidenciasvlc.mysqlservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}