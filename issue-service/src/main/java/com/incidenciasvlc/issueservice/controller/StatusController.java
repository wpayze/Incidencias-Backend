package com.incidenciasvlc.issueservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.incidenciasvlc.issueservice.model.Status;
import com.incidenciasvlc.issueservice.service.StatusService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public Flux<Status> getAllStatuses() {
        return statusService.getAllStatuses();
    }

    @GetMapping("/{id}")
    public Mono<Status> getStatusById(@PathVariable String id) {
        return statusService.getStatusById(id);
    }

    @PostMapping
    public Mono<Status> createStatus(@RequestBody Status status) {
        return statusService.createStatus(status);
    }

    @PutMapping("/{id}")
    public Mono<Status> updateStatus(@PathVariable String id, @RequestBody Status status) {
        return statusService.updateStatus(id, status);
    }
}
