package com.incidenciasvlc.mysqlservice.service;

import com.incidenciasvlc.mysqlservice.model.Issue;
import com.incidenciasvlc.mysqlservice.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public List<Issue> findAllIssues() {
        return issueRepository.findAll();
    }

    public Optional<Issue> findIssueById(Long id) {
        return issueRepository.findById(id);
    }

    public Issue createIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    public Issue updateIssue(Long id, Issue issueDetails) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada con id " + id));
        
        issue.setTitle(issueDetails.getTitle());
        issue.setDescription(issueDetails.getDescription());
        issue.setLocation(issueDetails.getLocation());
        issue.setUser(issueDetails.getUser());
        issue.setCategory(issueDetails.getCategory());
        issue.setStatus(issueDetails.getStatus());
        issue.setImageUrl(issueDetails.getImageUrl());
        
        return issueRepository.save(issue);
    }

    public void deleteIssue(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada con id " + id));
        issueRepository.delete(issue);
    }
}
