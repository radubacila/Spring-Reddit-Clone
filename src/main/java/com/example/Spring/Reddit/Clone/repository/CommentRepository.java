package com.example.Spring.Reddit.Clone.repository;

import com.example.Spring.Reddit.Clone.model.Comment;
import com.example.Spring.Reddit.Clone.model.Post;
import com.example.Spring.Reddit.Clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}
