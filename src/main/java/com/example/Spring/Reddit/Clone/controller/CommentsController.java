package com.example.Spring.Reddit.Clone.controller;

import com.example.Spring.Reddit.Clone.dto.CommentsDto;
import com.example.Spring.Reddit.Clone.model.Comment;
import com.example.Spring.Reddit.Clone.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments/")
public class CommentsController {

    private final CommentService commentService;
    @PostMapping
    public ResponseEntity<Void> createComments(@RequestBody CommentsDto commentsDto)
    {
        commentService.save(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>>getAllCommentsForPost(@PathVariable Long postId)
    {
       return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentsDto>>getAllCommentsForUser(@PathVariable String username)
    {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(username));
    }
}
