package com.example.Spring.Reddit.Clone.service;

import com.example.Spring.Reddit.Clone.dto.PostRequest;
import com.example.Spring.Reddit.Clone.dto.PostResponse;
import com.example.Spring.Reddit.Clone.exceptions.PostNotFoundException;
import com.example.Spring.Reddit.Clone.exceptions.SpringRedditException;
import com.example.Spring.Reddit.Clone.exceptions.SubredditNotFoundException;
import com.example.Spring.Reddit.Clone.model.Post;
import com.example.Spring.Reddit.Clone.model.Subreddit;
import com.example.Spring.Reddit.Clone.model.User;
import com.example.Spring.Reddit.Clone.repository.CommentRepository;
import com.example.Spring.Reddit.Clone.repository.PostRepository;
import com.example.Spring.Reddit.Clone.repository.SubredditRepository;
import com.example.Spring.Reddit.Clone.repository.UserRepository;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j

@Transactional
public class PostService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        User currentUser=authService.getCurrentUser();
        postRepository.save(mapToPost(postRequest,subreddit,currentUser));

    }

    @Transactional
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public PostResponse getPost(Long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new PostNotFoundException(id.toString()));
        return mapToDto(post);
    }

    @Transactional
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts=postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> getPostsByUsername(String username) {
        User user= userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
        List<Post> posts=postRepository.findByUser(user);
        return  posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private PostResponse mapToDto(Post post) {
        return PostResponse.builder().id(post.getPostID())
                .userName(post.getUser().getUsername())
                .subredditName(post.getSubreddit().getName())
                .postName(post.getPostName())
                .url(post.getUrl())
                .description(post.getDescription())
                .commentCount(commentRepository.findByPost(post).size())
                .duration(TimeAgo.using(post.getCreatedDate().toEpochMilli()))
                .build();
    }

    private Post mapToPost(PostRequest postRequest, Subreddit subreddit, User currentUser)
    {
        Post post=new Post();
        post.setPostID(postRequest.getPostId());
        post.setPostName(postRequest.getPostName());
        post.setUrl(postRequest.getUrl());
        post.setDescription(postRequest.getDescription());
        post.setUser(currentUser);
        post.setCreatedDate(Instant.now());
        post.setSubreddit(subreddit);
        if (post.getVoteCount()==null)
            post.setVoteCount(0);
        return post;
    }



}
