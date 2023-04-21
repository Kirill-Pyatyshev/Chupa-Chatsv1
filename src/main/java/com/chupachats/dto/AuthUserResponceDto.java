package com.chupachats.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthUserResponceDto {
    private String email;
    private String token;
}
