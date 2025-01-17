package com.metarash.backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JwtAuthenticationDto {
    private String token;
    private String refreshToken;
}
