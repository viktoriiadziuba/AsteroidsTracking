package com.viktoriia.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloseApproachData {

    @SerializedName("close_approach_date")
    @Expose
    private String closeApproachDate;

    @SerializedName("close_approach_date_full")
    @Expose
    private String closeApproachDateFull;

    @SerializedName("relative_velocity")
    @Expose
    private RelativeVelocity relativeVelocity;

    @SerializedName("miss_distance")
    @Expose
    private MissDistance missDistance;

    @SerializedName("orbiting_body")
    @Expose
    private String orbitingBody;

    public CloseApproachData() {

    }

    public CloseApproachData(String closeApproachDate, String closeApproachDateFull, RelativeVelocity relativeVelocity, MissDistance missDistance, String orbitingBody) {
        this.closeApproachDate = closeApproachDate;
        this.closeApproachDateFull = closeApproachDateFull;
        this.relativeVelocity = relativeVelocity;
        this.missDistance = missDistance;
        this.orbitingBody = orbitingBody;
    }

    public String getCloseApproachDate() {
        return closeApproachDate;
    }

    public void setCloseApproachDate(String closeApproachDate) {
        this.closeApproachDate = closeApproachDate;
    }

    public String getCloseApproachDateFull() {
        return closeApproachDateFull;
    }

    public void setCloseApproachDateFull(String closeApproachDateFull) {
        this.closeApproachDateFull = closeApproachDateFull;
    }

    public RelativeVelocity getRelativeVelocity() {
        return relativeVelocity;
    }

    public void setRelativeVelocity(RelativeVelocity relativeVelocity) {
        this.relativeVelocity = relativeVelocity;
    }

    public MissDistance getMissDistance() {
        return missDistance;
    }

    public void setMissDistance(MissDistance missDistance) {
        this.missDistance = missDistance;
    }

    public String getOrbitingBody() {
        return orbitingBody;
    }

    public void setOrbitingBody(String orbitingBody) {
        this.orbitingBody = orbitingBody;
    }
}
