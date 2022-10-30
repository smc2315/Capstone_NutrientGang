package com.dev.health.entity;

import lombok.Getter;

@Getter
public enum Activity {
    SOFT_ACTIVITY(25),
    NORMAL_ACTIVITY(30),
    HARD_ACTIVITY(40);

    private final int value;

    Activity(int value){
        this.value = value;
    }
}
