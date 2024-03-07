package com.incidenciasvlc.issueservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.incidenciasvlc.issueservice.model.Issue;
import com.incidenciasvlc.issueservice.service.IssueService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public Flux<Issue> getAllIssues() {
        return issueService.findAllIssues();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Issue>> getIssueById(@PathVariable String id) {
        return issueService.findIssueById(id)
                .map(issue -> ResponseEntity.ok(issue))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Issue> createIssue(@RequestBody Issue issue) {
        return issueService.saveIssue(issue);
    }
}
