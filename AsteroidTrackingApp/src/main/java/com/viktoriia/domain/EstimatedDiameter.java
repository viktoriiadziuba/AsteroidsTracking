package com.viktoriia.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EstimatedDiameter {

    @SerializedName("meters")
    @Expose
    private Meters meters;

    public EstimatedDiameter(Meters meters) {
        this.meters = meters;
    }

    public Meters getMeters() {
        return meters;
    }

    public void setMeters(Meters meters) {
        this.meters = meters;
    }
}
