package com.example.Spring.Reddit.Clone.service;

import com.example.Spring.Reddit.Clone.dto.CommentsDto;
import com.example.Spring.Reddit.Clone.exceptions.PostNotFoundException;
import com.example.Spring.Reddit.Clone.model.Comment;
import com.example.Spring.Reddit.Clone.model.NotificationEmail;
import com.example.Spring.Reddit.Clone.model.Post;
import com.example.Spring.Reddit.Clone.model.User;
import com.example.Spring.Reddit.Clone.repository.CommentRepository;
import com.example.Spring.Reddit.Clone.repository.PostRepository;
import com.example.Spring.Reddit.Clone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private static final String POST_URL="";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto)
    {
        Post post=postRepository.findById(commentsDto.getPostId())
                .orElseThrow(()->new PostNotFoundException(commentsDto.getPostId().toString()));
        commentRepository.save(mapCommentDto(commentsDto,post,authService.getCurrentUser()));

        String message=mailContentBuilder.build(post.getUser().getUsername()+" posted a comment on your post."+POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername()+" Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String username) {
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
        return commentRepository.findByUser(user)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private Comment mapCommentDto (CommentsDto commentsDto, Post post, User user)
    {
        return Comment.builder()
                .id(commentsDto.getId())
                .text(commentsDto.getText())
                .post(post)
                .createdDate(Instant.now())
                .user(user)
                .build();
    }

    private CommentsDto mapToDto(Comment comment)
    {
        return CommentsDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getPostID())
                .createdDate(comment.getCreatedDate())
                .text(comment.getText())
                .userName(comment.getUser().getUsername())
                .build();
    }
}
