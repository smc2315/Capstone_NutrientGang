package com.dev.health.entity;

import lombok.Getter;

@Getter
public enum Target {
    LOSS_WEIGHT(200,0.5f,0.3f,0.2f,-500),
    GAIN_MUSCLE(200,0.6f,0.3f,0.1f,500),
    MAINTAIN_WEIGHT(0,0.5f,0.3f,0.2f,0);

    private final int value;
    private final float carbohydratePortion;
    private final float proteinPortion;
    private final float fatPortion;

    private final int calorieValue;

    Target(int value,float carbohydratePortion,float proteinPortion, float fatPortion,int calorieValue){
        this.value = value;
        this.carbohydratePortion = carbohydratePortion;
        this.proteinPortion = proteinPortion;
        this.fatPortion = fatPortion;
        this.calorieValue = calorieValue;
    }
}
