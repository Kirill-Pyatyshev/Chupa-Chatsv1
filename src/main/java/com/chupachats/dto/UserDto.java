package com.chupachats.dto;

import lombok.*;

import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto{
    private Long id;
    private String name;
    private Integer age;
    private Date birthday;
    private String phoneNumber;
    private String email;
    private String gender; 
}
