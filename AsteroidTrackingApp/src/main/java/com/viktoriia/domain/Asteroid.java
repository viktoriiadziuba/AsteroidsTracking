package com.viktoriia.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Asteroid {

    @SerializedName("links")
    @Expose
    private Links links;

    @SerializedName("neo_reference_id")
    @Expose
    private String neoReferenceId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("estimated_diameter")
    @Expose
    private EstimatedDiameter estimatedDiameter;

    @SerializedName("is_potentially_hazardous_asteroid")
    @Expose
    private boolean isPotentiallyHazardousAsteroid;

    @SerializedName("close_approach_data")
    @Expose
    private List<CloseApproachData> closeApproachData;


    public Asteroid(Links links, String neoReferenceId, String name, EstimatedDiameter estimatedDiameter, boolean isPotentiallyHazardousAsteroid, List<CloseApproachData> closeApproachData) {
        this.links = links;
        this.neoReferenceId = neoReferenceId;
        this.name = name;
        this.estimatedDiameter = estimatedDiameter;
        this.isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid;
        this.closeApproachData = closeApproachData;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getNeoReferenceId() {
        return neoReferenceId;
    }

    public void setNeoReferenceId(String neoReferenceId) {
        this.neoReferenceId = neoReferenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EstimatedDiameter getEstimatedDiameter() {
        return estimatedDiameter;
    }

    public void setEstimatedDiameter(EstimatedDiameter estimatedDiameter) {
        this.estimatedDiameter = estimatedDiameter;
    }

    public boolean isPotentiallyHazardousAsteroid() {
        return isPotentiallyHazardousAsteroid;
    }

    public void setPotentiallyHazardousAsteroid(boolean potentiallyHazardousAsteroid) {
        isPotentiallyHazardousAsteroid = potentiallyHazardousAsteroid;
    }

    public List<CloseApproachData> getCloseApproachData() {
        return closeApproachData;
    }

    public void setCloseApproachData(List<CloseApproachData> closeApproachData) {
        this.closeApproachData = closeApproachData;
    }
}
