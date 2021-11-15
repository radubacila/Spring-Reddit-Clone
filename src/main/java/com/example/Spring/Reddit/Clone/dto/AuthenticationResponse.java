package com.example.Spring.Reddit.Clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponse {
    private String authenticationToken;
    private String username;
}
