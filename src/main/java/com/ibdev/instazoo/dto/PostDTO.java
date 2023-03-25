package com.ibdev.instazoo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PostDTO {

    private Long id;
    private String title;
    private String description;
    private String location;
    private String username;
    private Integer likes;

    private Set<String> usersLiked;

}
