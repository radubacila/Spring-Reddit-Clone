package com.example.Spring.Reddit.Clone.repository;

import com.example.Spring.Reddit.Clone.dto.PostResponse;
import com.example.Spring.Reddit.Clone.model.Post;
import com.example.Spring.Reddit.Clone.model.Subreddit;
import com.example.Spring.Reddit.Clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
