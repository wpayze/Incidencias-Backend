package com.incidenciasvlc.issueservice.controller;

import org.springframework.web.bind.annotation.*;
import com.incidenciasvlc.issueservice.model.Category;
import com.incidenciasvlc.issueservice.service.CategoryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Flux<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Mono<Category> getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public Mono<Category> createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }
}
