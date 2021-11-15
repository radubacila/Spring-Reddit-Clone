package com.example.Spring.Reddit.Clone.repository;

import com.example.Spring.Reddit.Clone.model.Post;
import com.example.Spring.Reddit.Clone.model.User;
import com.example.Spring.Reddit.Clone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
