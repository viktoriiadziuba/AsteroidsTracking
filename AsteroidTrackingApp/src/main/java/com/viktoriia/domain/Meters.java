package com.viktoriia.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meters {

    @SerializedName("estimated_diameter_min")
    @Expose
    private String estimatedDiameterMin;

    @SerializedName("estimated_diameter_max")
    @Expose
    private String estimatedDiameterMax;

    public Meters() {

    }

    public Meters(String estimatedDiameterMin, String estimatedDiameterMax) {
        this.estimatedDiameterMin = estimatedDiameterMin;
        this.estimatedDiameterMax = estimatedDiameterMax;
    }

    public String getEstimatedDiameterMin() {
        return estimatedDiameterMin;
    }

    public void setEstimatedDiameterMin(String estimatedDiameterMin) {
        this.estimatedDiameterMin = estimatedDiameterMin;
    }

    public String getEstimatedDiameterMax() {
        return estimatedDiameterMax;
    }

    public void setEstimatedDiameterMax(String estimatedDiameterMax) {
        this.estimatedDiameterMax = estimatedDiameterMax;
    }
}
