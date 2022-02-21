package com.example.blogapi.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTAuthResponse {
    private String accessToken;
    private final String TOKEN_TYPE = "Bearer";

    public JWTAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
