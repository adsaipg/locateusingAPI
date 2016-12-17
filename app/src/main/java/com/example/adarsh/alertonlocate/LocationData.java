package com.example.adarsh.alertonlocate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by adaRSH on 05-Oct-16.
 */
public interface LocationData {

    @GET("api/directions/json")
    Call<LocationAlert> loadList(@Query("origin") String origin, @Query("destination") String destination);
}
