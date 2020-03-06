package com.viktoriia.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MissDistance {

    @SerializedName("kilometers")
    @Expose
    private String kilometers;

    public MissDistance(String kilometers) {
        this.kilometers = kilometers;
    }

    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }
}
