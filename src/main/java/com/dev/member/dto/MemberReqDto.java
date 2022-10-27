package com.dev.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberReqDto {
    private String email;
    private String password;
    private String username;
    private Integer height;
    private Integer weight;
    private String gender;
    private String target;
    private String activity;

}
