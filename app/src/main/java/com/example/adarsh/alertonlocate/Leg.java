
package com.example.adarsh.alertonlocate;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;




public class Leg {

    @SerializedName("distance")
    @Expose
    private Distance distance;
    @SerializedName("duration")
    @Expose
    private Duration duration;
    @SerializedName("end_address")
    @Expose
    private String endAddress;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = new ArrayList<Step>();
    @SerializedName("end_location")
    @Expose
    private EndLocation endLocation;
    @SerializedName("start_address")
    @Expose
    private String startAddress;
    @SerializedName("start_location")
    @Expose
    private StartLocation startLocation;

    @SerializedName("traffic_speed_entry")
    @Expose
    private List<Object> trafficSpeedEntry = new ArrayList<Object>();
    @SerializedName("via_waypoint")
    @Expose
    private List<Object> viaWaypoint = new ArrayList<Object>();


    public Distance getDistance() {
        return distance;
    }


    public void setDistance(Distance distance) {
        this.distance = distance;
    }


    public Duration getDuration() {
        return duration;
    }


    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public EndLocation getEndLocation() {
        return endLocation;
    }


    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }


    public String getStartAddress() {
        return startAddress;
    }


    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public StartLocation getStartLocation() {
        return startLocation;
    }


    public void setStartLocation(StartLocation startLocation) {
        this.startLocation = startLocation;
    }


    public List<Object> getTrafficSpeedEntry() {
        return trafficSpeedEntry;
    }


    public void setTrafficSpeedEntry(List<Object> trafficSpeedEntry) {
        this.trafficSpeedEntry = trafficSpeedEntry;
    }

    public List<Object> getViaWaypoint() {
        return viaWaypoint;
    }


    public void setViaWaypoint(List<Object> viaWaypoint) {
        this.viaWaypoint = viaWaypoint;
    }
    public List<Step> getSteps() {
        return steps;
    }

    /**
     *
     * @param steps
     *     The steps
     */
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

}
