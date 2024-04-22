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

        if (issueDetails.getTitle() != null)
            issue.setTitle(issueDetails.getTitle());
        if (issueDetails.getDescription() != null)
            issue.setDescription(issueDetails.getDescription());
        if (issueDetails.getLatitude() != null)
            issue.setLatitude(issueDetails.getLatitude());
        if (issueDetails.getLongitude() != null)
            issue.setLongitude(issueDetails.getLongitude());
        if (issueDetails.getUser() != null)
            issue.setUser(issueDetails.getUser());
        if (issueDetails.getCategory() != null)
            issue.setCategory(issueDetails.getCategory());
        if (issueDetails.getStatus() != null)
            issue.setStatus(issueDetails.getStatus());
        if (issueDetails.getImageUrl() != null)
            issue.setImageUrl(issueDetails.getImageUrl());

        return issueRepository.save(issue);
    }

    public void deleteIssue(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada con id " + id));
        issueRepository.delete(issue);
    }
}
