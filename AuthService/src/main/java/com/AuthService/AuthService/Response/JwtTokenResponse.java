package com.AuthService.AuthService.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenResponse {
    private String jwtToken;
    private String type;
    private String validUntil;
}
