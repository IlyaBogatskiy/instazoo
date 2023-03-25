package com.ibdev.instazoo.service.interfaces;

import com.ibdev.instazoo.dto.CommentDTO;
import com.ibdev.instazoo.model.Comment;

import java.security.Principal;
import java.util.List;

public interface CommentService {

    Comment saveComment(Long id, CommentDTO commentDTO, Principal principal);

    List<Comment> getAllCommentsForPost(Long id);

    void deleteComment(Long id);
}
