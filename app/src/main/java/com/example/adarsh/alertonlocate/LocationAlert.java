
package com.example.adarsh.alertonlocate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class LocationAlert {

    @SerializedName("geocoded_waypoints")
    @Expose
     List<GeocodedWaypoint> geocodedWaypoints = new ArrayList<GeocodedWaypoint>();
    @SerializedName("routes")
    @Expose
     List<Route> routes = new ArrayList<Route>();

    public LocationAlert(List<GeocodedWaypoint> geocodedWaypoints, List<Route> routes, String status) {
        this.geocodedWaypoints = geocodedWaypoints;
        this.routes = routes;
        this.status = status;
    }

    @SerializedName("status")
    @Expose
     String status;


    public List<GeocodedWaypoint> getGeocodedWaypoints() {
        return geocodedWaypoints;
    }


    public void setGeocodedWaypoints(List<GeocodedWaypoint> geocodedWaypoints) {
        this.geocodedWaypoints = geocodedWaypoints;
    }


    public List<Route> getRoutes() {
        return routes;
    }


    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

}
