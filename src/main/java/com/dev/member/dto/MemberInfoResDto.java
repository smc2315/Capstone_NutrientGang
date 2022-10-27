package com.dev.member.dto;

import com.dev.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoResDto {
    private String email;

    public static MemberInfoResDto of(Member member){
        return new MemberInfoResDto(member.getEmail());
    }
}
