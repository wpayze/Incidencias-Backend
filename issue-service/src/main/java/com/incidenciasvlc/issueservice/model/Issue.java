package com.incidenciasvlc.issueservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class Issue {
    private Long id;

    private List<Comment> comments;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede tener más de 255 caracteres")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    private String address;

    private Double latitude;
    private Double longitude;

    private User user;
    private Category category;
    private Status status;

    @Size(max = 255, message = "La URL de la imagen no puede tener más de 255 caracteres")
    private String imageUrl;

    private Date createdAt;
    private Date updatedAt;
}
