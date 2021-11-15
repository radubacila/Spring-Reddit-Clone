package com.example.Spring.Reddit.Clone.service;

import com.example.Spring.Reddit.Clone.dto.VoteDto;
import com.example.Spring.Reddit.Clone.exceptions.PostNotFoundException;
import com.example.Spring.Reddit.Clone.exceptions.SpringRedditException;
import com.example.Spring.Reddit.Clone.model.Post;
import com.example.Spring.Reddit.Clone.model.Vote;
import com.example.Spring.Reddit.Clone.repository.PostRepository;
import com.example.Spring.Reddit.Clone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.example.Spring.Reddit.Clone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + voteDto.getPostId()));
        Optional<Vote>voteByPostAndUser=voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType()))
        {
            throw new SpringRedditException("You have already "+voteDto.getVoteType()+"'d this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType()))
        {
            post.setVoteCount(post.getVoteCount()+1);
        }
        else
        {
            post.setVoteCount(post.getVoteCount()-1);
        }
        voteRepository.save(mapToVote(voteDto,post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
