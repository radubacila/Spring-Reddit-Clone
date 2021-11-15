package com.example.Spring.Reddit.Clone.model;

import com.example.Spring.Reddit.Clone.exceptions.SpringRedditException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1),DOWNVOTE(-1),
    ;

    private int direction;

    VoteType(int direction)
    {
    }

    public static VoteType lookup(Integer direction)
    {
        return Arrays.stream(VoteType.values())
                .filter(value->value.equals(direction))
                .findAny()
                .orElseThrow(()->new SpringRedditException("Vote not found"));
    }
}
