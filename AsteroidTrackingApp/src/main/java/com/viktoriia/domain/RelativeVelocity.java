package com.viktoriia.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelativeVelocity {

    @SerializedName("kilometers_per_hour")
    @Expose
    private String kilometersPerHour;

    public RelativeVelocity() {

    }

    public RelativeVelocity(String kilometersPerHour) {
        this.kilometersPerHour = kilometersPerHour;
    }

    public String getKilometersPerHour() {
        return kilometersPerHour;
    }

    public void setKilometersPerHour(String kilometersPerHour) {
        this.kilometersPerHour = kilometersPerHour;
    }
}
