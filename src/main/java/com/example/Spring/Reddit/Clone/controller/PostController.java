package com.example.Spring.Reddit.Clone.controller;

import com.example.Spring.Reddit.Clone.dto.PostRequest;
import com.example.Spring.Reddit.Clone.dto.PostResponse;
import com.example.Spring.Reddit.Clone.model.Post;
import com.example.Spring.Reddit.Clone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest)
    {
        postService.save(postRequest);
        return  new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("/by-subreddit/{id}")
    public List<PostResponse>getPostsBySubreddit(@PathVariable Long id)
    {
        return  postService.getPostsBySubreddit(id);
    }
    @GetMapping("/by-user/{username}")
    public List<PostResponse>getPostsByUsername(@PathVariable String username)
    {
        return postService.getPostsByUsername(username);
    }

}
