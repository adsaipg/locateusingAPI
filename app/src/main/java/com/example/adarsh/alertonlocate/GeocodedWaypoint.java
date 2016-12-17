
package com.example.adarsh.alertonlocate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GeocodedWaypoint {

    @SerializedName("geocoder_status")
    @Expose
     String geocoderStatus;
    @SerializedName("partial_match")
    @Expose
     Boolean partialMatch;
    @SerializedName("place_id")
    @Expose
     String placeId;

    public GeocodedWaypoint(String geocoderStatus, Boolean partialMatch, String placeId, List<String> types) {
        this.geocoderStatus = geocoderStatus;
        this.partialMatch = partialMatch;
        this.placeId = placeId;
        this.types = types;
    }

    @SerializedName("types")
    @Expose
     List<String> types = new ArrayList<String>();


    public String getGeocoderStatus() {
        return geocoderStatus;
    }


    public void setGeocoderStatus(String geocoderStatus) {
        this.geocoderStatus = geocoderStatus;
    }


    public Boolean getPartialMatch() {
        return partialMatch;
    }


    public void setPartialMatch(Boolean partialMatch) {
        this.partialMatch = partialMatch;
    }


    public String getPlaceId() {
        return placeId;
    }


    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }


    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

}
