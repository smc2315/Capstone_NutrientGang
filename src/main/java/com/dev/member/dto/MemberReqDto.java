package com.dev.member.dto;

import com.dev.health.entity.Activity;
import com.dev.health.entity.Gender;
import com.dev.health.entity.Target;
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
    private Gender gender;
    private Target target;
    private Activity activity;

}
