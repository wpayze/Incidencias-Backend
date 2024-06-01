package com.incidenciasvlc.mysqlservice.repository;

import com.incidenciasvlc.mysqlservice.model.Issue;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByUserId(Long userId);
}
