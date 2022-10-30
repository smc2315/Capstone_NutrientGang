package com.dev.health.entity;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(22),
    FEMALE(21);

    private final int value;

    Gender(int value){
        this.value = value;
    }

}
