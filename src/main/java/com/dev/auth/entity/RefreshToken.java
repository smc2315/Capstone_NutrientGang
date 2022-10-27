package com.dev.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "refresh_token")
@Getter
@NoArgsConstructor
public class RefreshToken {
    @Id
    @Column
    private String key;
    @Column
    private String value;

    @Builder
    public RefreshToken(String key,String value){
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token){
        this.value = token;
        return this;
    }

}
