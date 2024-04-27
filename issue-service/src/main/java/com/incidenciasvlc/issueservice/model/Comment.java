package com.incidenciasvlc.issueservice.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
    private String _id;
    private Integer issueId;
    private Integer userId;
    private String content;
    private Date createdAt;
    private String parentId;

    private User user;
}
