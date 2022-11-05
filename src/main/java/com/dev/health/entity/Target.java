package com.dev.health.entity;

import lombok.Getter;

@Getter
public enum Target {
    LOSS_WEIGHT(-500,0.5f,0.3f,0.2f),
    GAIN_MUSCLE(500,0.6f,0.3f,0.1f),
    MAINTAIN_WEIGHT(0,0.5f,0.3f,0.2f);

    private final int value;
    private final float carbohydratePortion;
    private final float proteinPortion;
    private final float fatPortion;


    Target(int value,float carbohydratePortion,float proteinPortion, float fatPortion){
        this.value = value;
        this.carbohydratePortion = carbohydratePortion;
        this.proteinPortion = proteinPortion;
        this.fatPortion = fatPortion;

    }
}
