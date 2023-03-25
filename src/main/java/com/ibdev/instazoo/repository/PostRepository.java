package com.ibdev.instazoo.repository;

import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserOrderByCreatedDateDesc(User user);

    List<Post> findAllByOrderByCreatedDateDesc();

    Optional<Post> findFindPostByIdAndUser(Long id, User user);
}
