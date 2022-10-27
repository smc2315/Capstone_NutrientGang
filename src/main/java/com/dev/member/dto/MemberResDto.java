package com.dev.member.dto;

import com.dev.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResDto {
    private String email;

    public static MemberResDto of(Member member){
        return new MemberResDto(member.getEmail());
    }
}
