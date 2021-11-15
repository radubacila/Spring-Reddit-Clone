package com.example.Spring.Reddit.Clone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postID;
    @NotBlank(message="Post name cannot be empty or NULL")
    private String postName;
    @Nullable
    private String url;
    @Nullable
    @Lob
    private String description;
    //@Column(nullable = false, columnDefinition = "int default 0")
    private Integer voteCount;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name="userId", referencedColumnName = "userId")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id",referencedColumnName = "id")
    private Subreddit subreddit;
}
