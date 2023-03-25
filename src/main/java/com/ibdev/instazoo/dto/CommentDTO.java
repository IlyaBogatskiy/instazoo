package com.ibdev.instazoo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CommentDTO {

    private Long id;
    @NotEmpty
    private String message;
    @NotEmpty
    private String username;
}
