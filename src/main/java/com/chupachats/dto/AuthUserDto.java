package com.chupachats.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthUserDto{
    private String email;
    private String password;
}
